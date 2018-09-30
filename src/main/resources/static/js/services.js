$(document).ready(
    function () {
        $.ajax({
            url:"http://localhost:8080/services/",
            type:"GET",
            success:function(msg){
                window.data = msg;
                var select = $("#services");
                select.html("");
                for (var a of msg) {
                    select.append($("<option>").attr("value", a.id).text(a.name))
                }
            },
            dataType:"json"
        });
    }
);