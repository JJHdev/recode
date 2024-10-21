async function sha256(message){
    const msgBuffer = new TextEncoder().encode(message);
    const hashBuffer = await crypto.subtle.digest('SHA-256', msgBuffer);
    const hashArray = Array.from(new Uint8Array(hashBuffer));
    return hashArray.map(b => b.toString(16).padStart(2, '0')).join('');
}

$(document).ready(function() {

    $('.userForm').submit(async function (event) {
        event.preventDefault(); // 폼 기본 제출 방지

        $('#password').val(await sha256($('#password').val())); // 비밀번호 필드에 해시된 비밀번호 설정
        $('#passwordChck').val(await sha256($('#passwordChck').val())); // 비밀번호 확인 필드에 해시된 비밀번호 설정

        document.querySelector('.userForm').submit();
    });


    $('#id-Check-Btn').click(function () {
        $.ajax({
            type: 'post',
            url: contextPath + 'user/checkUser',
            data: {userId: $('#userId').val()},
            success: function (data) {
                if (data.success) {
                    alert(data.message); // 성공 메시지 표시
                } else {
                    alert(data.message); // 실패 메시지 표시
                }
            },
            error: function (xhr, status, error) {
                alert('아이디 체크에 실패하였습니다.: ' + error);
            }
        });
    });

    $('#mail-Send-Btn').click(function () {
        const email = $('#email1').val() + $('#email2').val();
        $.ajax({
            type: 'post',
            url: contextPath  + 'email/send',
            data: {email: email},
            success: function () {
                $('#email_number').attr('disabled', false);
                $('#mail-Check-Btn').attr('disabled', false).show(); // 버튼 활성화 및 표시
                alert('인증번호가 전송되었습니다.');
            },
            error: function (xhr, status, error) {
                alert('이메일 전송에 실패했습니다: ' + error);
            }
        });
    });

    $('#mail-Check-Btn').click(function () {
        let email1 = $('#email1').val();
        let email2 = $('#email2').val();
        let email = email1 + email2;
        const code = $('#email_number').val();
        $.ajax({
            type: 'post',
            url: contextPath + 'email/verify',
            data: {email: email, code: code},
            success: function (data) {
                if (data.success) {
                    $('#email2_hidden').val(email2); // 선택한 값을 숨겨진 입력 필드에 설정
                    $('#email_number').attr('disabled', true);
                    $('#mail-Check-Btn').attr('disabled', true).hide(); // 버튼 비활성화 및 숨기기
                    $('#email1').attr('readonly', true);
                    $('#email2').attr('disabled', true); // select 요소 비활성화
                    $('#mail-Send-Btn').attr('disabled', true).text('인증완료');
                    alert('인증이 완료되었습니다.');
                } else {
                    alert(data.message); // 실패 메시지 표시
                }
            },
            error: function (xhr, status, error) {
                alert('이메일 전송에 실패했습니다: ' + error);
            }
        });
    });

});