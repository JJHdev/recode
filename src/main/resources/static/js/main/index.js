$(document).ready(function(){
    $('#resumeButton').on('click', function (event) {
        event.preventDefault(); // 기본 동작(페이지 이동) 방지
        authAnchor('/nextPage'); // authAnchor 호출
    });
});