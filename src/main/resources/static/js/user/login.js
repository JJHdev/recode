async function sha256(message){
    const msgBuffer = new TextEncoder().encode(message);
    const hashBuffer = await crypto.subtle.digest('SHA-256', msgBuffer);
    const hashArray = Array.from(new Uint8Array(hashBuffer));
    return hashArray.map(b => b.toString(16).padStart(2, '0')).join('');
}

function redirectTo(url) {
    window.location.href = contextPath +  url;
}


$(document).ready(function() {
    $('.loginForm').submit(async function (event) {
        event.preventDefault(); // 폼 기본 제출 방지
        $('#password').val(await sha256($('#password').val())); // 비밀번호 필드에 해시된 비밀번호 설정
        document.querySelector('.loginForm').submit();
    });
});