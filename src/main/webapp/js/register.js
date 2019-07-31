$(function () {
    $("#registerForm").submit(function () {
        if (checkUsername() && checkPassword() && checkEmail()) {
            $.post("registerServlet", $(this).serialize(), function (data) {
                alert(data.errorMsg);
                // 处理服务器响应数据
            })
        }
        // 不让页面跳转, 没有返回值或返回true表单提交
        return false;
    });

    $("#username").blur(checkUsername);
    $("#password").blur(checkPassword);
    $("#email").blur(checkEmail);

});


function checkUsername() {
    var username = $("#username").val();
    var reg_username = /^\w{8,20}$/;
    var flag = reg_username.test(username);
    if (flag) {
        $("username").css("border", "");
    } else {
        $("username").css("border", "1px, solid red");
    }
    return flag;
}

function checkPassword() {
    var password = $("#password").val();
    var reg_pw = /^\w{8,20}$/;
    var flag = reg_pw.test(password);
    if (flag) {
        //密码合法
        $("#password").css("border", "");
    } else {
        //密码非法,加一个红色边框
        $("#password").css("border", "1px solid red");
    }
    return flag;
}

//校验邮箱
function checkEmail() {
    //1.获取邮箱
    var email = $("#email").val();
    //2.定义正则      itcast@163.com
    var reg_email = /^\w+@\w+\.\w+$/;
    //3.判断
    var flag = reg_email.test(email);
    if (flag) {
        $("#email").css("border", "");
    } else {
        $("#email").css("border", "1px solid red");
    }
    return flag;
}

