"use strict";

$(function () {

    $("#registerSubmit").click(function() {
        $.ajax({
            url: "/register",
            type: 'POST',
            data:$("#registerForm").serialize(),
            success: function(data){
                if (data.success) {
                    location.reload();
                } else {
                    toastr.error(data.message);
                }

            },
            error : function() {
                toastr.error("error!");
            }
        });
    });

});