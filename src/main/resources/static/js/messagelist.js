document.addEventListener('DOMContentLoaded', function(event) {
    document.querySelectorAll('.delrestore')
        .forEach( el => el.addEventListener('click', function(event){
            delRestore(this.id);
            return false;
        }));
})


async function delRestore(msgId){
    let a = document.getElementById(msgId);
    let shouldDelete = a.classList.contains('delLink');
    let form = document.getElementById("csrf_form");
    let csrfVal = form.getElementsByTagName('input')[0].value;
    let url = '/message/restore/' + msgId;
    if(shouldDelete){
        url = '/message/delete/' + msgId;
    }
    let response = await fetch(url,  {
        method: 'POST',
        headers: {
            'X-CSRF-TOKEN': csrfVal
        },
    });
    let result = await response.json();
    let tr = document.getElementById('row_' + msgId);
    debugger
    if(shouldDelete){
        tr.classList.add('text-muted');
        a.classList.remove('delLink');
        a.classList.add('restoreLink');
        a.parentNode.querySelector('span').innerText = 'Igen'
        a.textContent = '(Visszaállít)';
    }
    else{
        tr.classList.remove('text-muted');
        a.classList.remove('restoreLink');
        a.classList.add('delLink');
        a.parentNode.querySelector('span').innerText = 'Nem'
        a.textContent = '(Töröl)';
    }
}