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
    let body = shouldDelete ? {'action': "DELETE"} : {'action' : 'RESTORE'};
    if(shouldDelete){
        url = '/message/delete/' + msgId;
    }
    //Send http POST request from javascript
    let response = await fetch('/rest/messages/' + msgId,  {
        method: 'Put',
        headers: {
            'X-CSRF-TOKEN': csrfVal,
            'Content-Type': 'application/json;charset=utf-8'
        },
        body: JSON.stringify(body)
    });
    let result = await response.json();
    //Response arrived, let's modify the row from deleted to undeleted or vice versa
    let tr = document.getElementById('row_' + msgId);
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