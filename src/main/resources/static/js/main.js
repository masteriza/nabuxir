$(document).ready(function () {

    $('#btSingIn').click(function () {
        var user = {};

        user["email"] = $("#email").val();
        user["password"] = $("#password").val();

        $.ajax({
            type: "POST",
            contentType: "application/json",
            url: "/oauth/token",
            data: JSON.stringify(user),
            dataType: 'json',
            success: function (responseData) {
                if (responseData != "") {
                    $(".errorSummary").empty().append(responseData);
                } else {
                    location.href = 'message.jsp';
                }
            }
        });
    });

});

$('.submit').on('click', singIn);


function singIn() {
    var user = {};

    user["email"] = $("#email").val();
    user["password"] = $("#password").val();

    //let info = JSON.stringify(user);
    let info = {};
    info["grant_type"] = "password";
    info["username"] = "admin";
    info["password"] = "admin1234";
    info["client_id"] = "spring-security-oauth2-read-write-client";

    console.log(info);

    $.ajax({
        type: "POST",
        url: "/oauth/token",

        data: info,
        headers: {
            "Authorization": "Basic " + btoa("spring-security-oauth2-read-write-client" + ":" + "spring-security-oauth2-read-write-client-password1234"),
            "Content-Type": "application/x-www-form-urlencoded"
        },
        success: function (responseData) {
            if (localStorage) {
                try {
                    localStorage.setItem('access_token', responseData.access_token);
                    localStorage.setItem('token_type', responseData.token_type);
                    localStorage.setItem('refresh_token', responseData.refresh_token);
                    localStorage.setItem('expires_in', responseData.expires_in);
                    localStorage.setItem('scope', responseData.scope);
                    loginToIndexPage();

                } catch (e) {
                    if (e == QUOTA_EXCEEDED_ERR) {
                        alert('Sorry, something went wrong_\n' +
                            'SessionStorage is full\n' +
                            'Let us know about it, we will be grateful to you.');
                        //todo: залогировать это в БД
                    }
                }
            } else {
                alert("Sorry, your browser do not support session storage.");
            }
        }, error: function (error) {
            console.log(error);
        }
    });
    return false;
    // data = $("#registrationForm").serializeArray();
    // $.ajax({
    //     url: 'http://localhost:8080/profiles',
    //     method: 'POST',
    //     dataType: 'text', //You can also define this as Json
    //     data: data,
    //     success: function () { /*doSomething();*/
    //     },
    //     error: function (error) {/*doSomething();*/
    //     }
    // });
}

function loginToIndexPage() {
    // alert(localStorage.getItem("access_token"));
    $.ajax({
        type: "GET",
        url: "http://localhost:8080/api/index.html",
        headers: {
            "Authorization": "Bearer " + localStorage.getItem("access_token"),
            "Content-Type": "application/json"
        },

        success: function (responseData) {
            alert('zazaza')
            window.location.href = "http://localhost:8080/api/index.html"
            console.log(responseData);
        }, error: function (error) {
            console.log(error);
        }
    });
    // return false;
}

function loginToIndexPageZ() {
    var XHR = new XMLHttpRequest();
    var urlEncodedData = "";
    var urlEncodedDataPairs = [];
    var name;

    // Set up our request
    XHR.open('GET', 'http://localhost:8080/api/index.html');

    // Add the required HTTP header for form data POST requests
    XHR.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');

    // Finally, send our data.
    XHR.send(urlEncodedData);
}