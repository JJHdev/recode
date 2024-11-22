function authFetch(url, options = {}) {
    const token = localStorage.getItem('accessToken');
    const headers = {
        ...options.headers,
        'Authorization': 'Bearer ' + token
    };

    return fetch(url, {
        ...options,
        headers
    });
}

function authSubmit(formId, url, method = 'POST') {
    const form = document.getElementById(formId); // 폼 ID를 가져옴
    if (!form) {
        console.error(`Form with ID '${formId}' not found.`);
        return;
    }

    // JWT 토큰 가져오기
    const token = localStorage.getItem('accessToken');
    if (!token) {
        console.error('No access token found in localStorage.');
        return;
    }

    // 숨겨진 필드 생성 또는 업데이트
    let tokenField = form.querySelector('input[name="accessToken"]');
    if (!tokenField) {
        tokenField = document.createElement('input');
        tokenField.type = 'hidden';
        tokenField.name = 'accessToken';
        form.appendChild(tokenField);
    }
    tokenField.value = token;

    // 폼의 URL과 메서드 설정
    form.action = url;
    form.method = method;

    // 폼 제출
    form.submit();
}