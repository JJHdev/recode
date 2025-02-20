const delProjectsList = [];
const delFilesList = [];


function onClickButton(buttonId) {
    switch (buttonId) {
        case "addProjectBtn":
            addProject()
            break;
        case "delProjectBtn":
            delProject()
            break;
        default:
            console.log("Unknown button ID:", buttonId);
            break;
    }
}

function addProject() {
    const container = document.querySelector('.project-container');
    const index = container.querySelectorAll('.project-form').length;
    const newHTML = `
        <div class="card overflow-hidden shadow rounded-4 border-0 mb-5 project-form">
            <div class="card-body p-0">
                <div class="d-flex align-items-center">
                    <div class="p-5" style="width:100%;">
                        <div class="text-primary fw-bolder mb-3">
                            <input type="date" class="form-control d-inline w-auto" name="projectList[0].startDate" value="" />
                            ~
                            <input type="date" class="form-control d-inline w-auto" name="projectList[0].endDate" value="" />
                        </div>
                        <h2 class="fw-bolder" >
                            <input type="text" class="form-control" name="projectList[${index}].title" value="" placeholder="Enter Title" />
                        </h2>
                        <p>
                            <textarea class="form-control" rows="12" name="projectList[${index}].content" placeholder="Enter Sub Content"></textarea>
                        </p>
                    </div>
                    <div>
                        <img class="img-fluid" src="https://dummyimage.com/300x400/343a40/6c757d" style="width: 400px; max-height: 400px;"  alt="..." />
                        <input type="file" class="form-control" name="fileList[${index}].file" multiple>
                        <input type="hidden" name="fileList[${index}].seqCode">
                    </div>
                </div>
            </div>
        </div>
    `;
    // 새로운 Form 추가
    container.insertAdjacentHTML('beforeend', newHTML);
}

function delProject() {
    const container = document.querySelector('.project-container');
    const forms = container.querySelectorAll('.project-form');
    const lastForm = forms[forms.length - 1];
    if (lastForm) {

        const projectSeqCodeInput = lastForm.querySelector('input[type="hidden"][name^="projectList"][name$=".seqCode"]');
        if (projectSeqCodeInput && projectSeqCodeInput.value) {
            delProjectsList.push(projectSeqCodeInput.value);
            const delInput = document.getElementById('delProjectCode');
            if (delInput) {
                delInput.value = delProjectsList.join(',');
            }
        }

        const fileSeqCodeInput = lastForm.querySelector('input[type="hidden"][name^="fileList"][name$=".seqCode"]');
        if (fileSeqCodeInput && fileSeqCodeInput.value) {
            delFilesList.push(fileSeqCodeInput.value);
            const delInput = document.getElementById('delFileCode');
            if (delInput) {
                delInput.value = delFilesList.join(',');
            }
        }

        lastForm.remove();
    } else {
        console.warn('No experience form to delete.');
    }
}