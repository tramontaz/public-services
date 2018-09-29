function SendPost() {
    //отправляю POST запрос и получаю ответ
    $$a({
        type: 'post',//тип запроса: get,post либо head
        url: '/services/',//url адрес файла обработчика
        response: 'text'//тип возвращаемого ответа text либо xml
    });
}
