// 表单提交前的自定义验证
document.querySelector('form').addEventListener('submit', (e) => {
    const username = document.getElementById('username').value;
    const password = document.getElementById('password').value;

    if (username.length < 3) {
        alert('用户名不能少于3位！');
        e.preventDefault();
        return;
    }
    if (password.length < 6) {
        alert('密码不能少于6位！');
        e.preventDefault();
        return;
    }
});