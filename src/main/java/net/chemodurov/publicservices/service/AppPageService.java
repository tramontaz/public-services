package net.chemodurov.publicservices.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.chemodurov.publicservices.model.Application;
import net.chemodurov.publicservices.model.Declarant;
import net.chemodurov.publicservices.model.Department;
import net.chemodurov.publicservices.model.StateService;
import net.chemodurov.publicservices.model.rest.DataFromApplicationPage;
import net.chemodurov.publicservices.model.rest.DataTable;
import net.chemodurov.publicservices.repository.PaginatedRepository;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Slf4j
@RequiredArgsConstructor
public class AppPageService {
    private static final long EIGHTEEN_YEARS_IN_MILSECS = 567993600000L;
    private static final String VALIDATE_OK = "ok";
    private final ApplicationService applicationService;
    private final DepartmentService departmentService;
    private final StateServiceService stateServiceService;
    private final PaginatedRepository paginatedRepository;

    public Map<String, String> validateAndSave(DataFromApplicationPage data) {
        Map<String, String> result = validate(data);
        if (result != null && VALIDATE_OK.equals(result.get("data"))) {
            try {
                Application application = new Application();
                StateService stateService = stateServiceService.findById(result.get("stateService"));
                Department department = departmentService.findDepartmentByCode(Integer.parseInt(result.get("department")));
                Declarant declarant = Declarant.builder()
                        .surname(result.get("surname"))
                        .name(result.get("name"))
                        .patronymic(result.get("patronymic"))
                        .email(result.get("email"))
                        .phoneNumber(Long.parseLong(result.get("phoneNumber")))
                        .dob(new Date(Long.parseLong(result.get("dob")))).build();
                application.setDate(new Date());
                application.setStateService(stateService);
                application.setDepartment(department);
                application.setDeclarant(declarant);
                applicationService.save(application);
            } catch (Exception e) {
                result.put("data", "error");
            }
        } else {
            if (result == null) {
                result = new HashMap<>();
                result.put("data", "empty");
            }
        }
        log.info("Result: {}", result);
        return result;
    }

    public Map<String, Object> getAll() {
        Map<String, Object> result = new HashMap<>();
        List<Application> applications = applicationService.findAll();
        if (applications != null && applications.size() > 0) {
            result.put("data", applications);
        } else {
            result.put("data", "empty");
        }
        return result;
    }

    public Map<String, Object> getAllByPages(Map<String, Object> request) {
        Map<String, Object> result = new HashMap<>();
        long total = applicationService.getTotalrecordsCount();
        List<Application> applications = paginatedRepository.paginatedSearchRequest(
                (String) request.get("searchParam"),
                (int) request.get("start"),
                (int) request.get("length"));
        if (applications != null && applications.size() > 0) {
            result.put("data", mapApplicationToDataTable(applications));
            result.put("draw", request.get("draw"));
            result.put("recordsTotal", total);
            result.put("recordsFiltered", total);
        } else {
            result.put("data", new ArrayList<>());
            result.put("draw", request.get("draw"));
            result.put("recordsTotal", total);
            result.put("recordsFiltered", 0);
        }
        return result;
    }

    private List<DataTable> mapApplicationToDataTable(List<Application> applications) {
        List<DataTable> result = new ArrayList<>();
        for (Application application : applications) {
            DataTable dataTable = DataTable.builder().build();
            if (application == null) {
                result.add(null);
                continue;
            }
            if (application.getStateService() != null && application.getStateService().getName() != null
                    && application.getStateService().getDepCode() != null && application.getStateService().getId() != null) {
                dataTable.setNumber(application.getStateService().getId());
                Department department = departmentService.findDepartmentByCode(application.getStateService().getDepCode());
                dataTable.setNameOfService(application.getStateService().getName());
                if (department != null && department.getName() != null)
                    dataTable.setNameOfDepartment(department.getName());
            }
            if (application.getDeclarant() != null) {
                StringBuilder fio = new StringBuilder();
                if (application.getDeclarant().getSurname() != null)
                    fio.append(application.getDeclarant().getSurname());
                if (application.getDeclarant().getName() != null)
                    fio.append(" ").append(application.getDeclarant().getName());
                if (application.getDeclarant().getPatronymic() != null)
                    fio.append(" ").append(application.getDeclarant().getPatronymic());
                dataTable.setFio(String.valueOf(fio));
                if (application.getDeclarant().getDob() != null)
                    dataTable.setDob(application.getDeclarant().getDob());
                if (application.getDeclarant().getEmail() != null)
                    dataTable.setEmail(application.getDeclarant().getEmail());
                if (application.getDeclarant().getPhoneNumber() != null) {
                    dataTable.setPhoneNumber(application.getDeclarant().getPhoneNumber());
                }
                dataTable.setDate(application.getDate());
                result.add(dataTable);
            }
        }
        if (result.size() > 0) {
            return result;
        } else return null;
    }

    private Map<String, String> validate(DataFromApplicationPage data) {
        Map<String, String> result = new HashMap<>();
        if (data == null) {
            result.put("data", "empty");
            return result;
        } else
            result.put("data", "ok");

        if (data.getId() == null) {
            result.put("stateService", "empty");
            result.put("data", "error");
        } else {
            StateService stateService = stateServiceService.findById(data.getId());
            if (stateService != null) {
                result.put("stateService", data.getId());
                result.put("department", String.valueOf(stateService.getDepCode()));
            } else {
                result.put("stateService", "empty");
                result.put("data", "error");
            }
        }
        if (data.getSurname() == null) {
            result.put("surname", "empty");
            result.put("data", "error");
        } else if (data.getSurname().length() > 100) {
            result.put("phoneNumber", "Слишком длинная фамилия!");
            result.put("data", "error");
        } else
            result.put("surname", data.getSurname());
        if (data.getName() == null) {
            result.put("name", "empty");
            result.put("data", "error");
        } else if (data.getName().length() > 100) {
            result.put("name", "Слишком длинное имя!");
            result.put("data", "error");
        } else
            result.put("name", data.getName());
        if (data.getPatronymic() != null && data.getPatronymic().length() > 100) {
            result.put("patronymic", "Слишком длинное отчество!");
            result.put("data", "error");
        } else if (data.getPatronymic() != null && data.getPatronymic().length() > 0) {
            result.put("patronymic", data.getPatronymic());
        }
        if (data.getEmail() != null && data.getEmail().length() > 0 &&!isValidEmail(data.getEmail())) {
            result.put("email", "Проверьте формат email!");
            result.put("data", "error");
        } else if (data.getEmail() != null && isValidEmail(data.getEmail())) {
            result.put("email", data.getEmail());
        }
        if (data.getPhoneNumber() == null)
            result.put("phoneNumber", "empty");
        else if (String.valueOf(data.getPhoneNumber()).length() > 0 && String.valueOf(data.getPhoneNumber()).length() != 11) {
            result.put("phoneNumber", "Телефон должен состоять из 11 цифр!");
            result.put("data", "error");
        } else
            result.put("phoneNumber", String.valueOf(data.getPhoneNumber()));
        if (data.getDob() == null) {
            result.put("dob", "empty");
            result.put("data", "error");
        } else if ((data.getDob().getTime() + EIGHTEEN_YEARS_IN_MILSECS) > new Date().getTime()) {
            result.put("dob", "Вы должны быть старше 18 лет!");
            result.put("data", "error");
        } else
            result.put("dob", String.valueOf(data.getDob().getTime()));
        return result;
    }

    private boolean isValidEmail(String email) {
        final String EMAIL_REGEX = "^[\\w-+]+(\\.[\\w]+)*@[\\w-]+(\\.[\\w]+)*(\\.[a-z]{2,})$";
        Pattern pattern = Pattern.compile(EMAIL_REGEX, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

}
