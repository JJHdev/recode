$(document).ready(function() {
    $('#mail-Send-Btn').click(function () {
        $.ajax({
            type: 'post',
            url: contextPath  + 'email/contactSend',
            data: {sendRegiName: $('#sendRegiName').val(),sendRegiEmail: $('#sendRegiEmail').val()
                  ,sendRegiPhone: $('#sendRegiPhone').val(),sendRegiNote: $('#sendRegiNote').val()},
            success: function () {
                alert('문의사항이 정상적으로 발송되었습니다.');
            },
            error: function (xhr, status, error) {
                alert('이메일 전송에 실패했습니다: ' + error);
            }
        });
    });
});