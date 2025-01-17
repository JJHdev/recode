$(document).ready(function () {
    // $('#resumeButton').on('click', function (event) {
    //   event.preventDefault(); // 기본 동작(페이지 이동) 방지
    //  authAnchor('/nextPage'); // authAnchor 호출
    //});
});

const delExperienceCodes = [];
const delEducationCode = [];
const delSkillCode = [];
const delLanguagesCode = [];


function onClickButton(buttonId) {
    switch (buttonId) {
        case "addExperiencesBtn":
            addExperiences()
            break;
        case "delExperiencesBtn":
            delExperiences()
            break;
        case "addEducationBtn":
            addEducation()
            break;
        case "delEducationBtn":
            delEducation()
            break;
        case "addSkillBtn":
            addSkill()
            break;
        case "delSkillBtn":
            delSkill()
            break;
        case "addLanguagesBtn":
            addLanguages()
            break;
        case "delLanguagesBtn":
            delLanguages()
            break;
        default:
            console.log("Unknown button ID:", buttonId);
            break;
    }
}

function onClickDelButton(buttonId){
    switch (buttonid) {
        case "delExperiencesBtn":

    }
}

function addExperiences() {
    const container = document.querySelector('.experience-container');
    const index = container.querySelectorAll('.experience-form').length;
    const newHTML = `
        <div class="card-body p-1 experience-form">
            <div class="row align-items-center gx-5">
                <div class="col text-center text-lg-start mb-4 mb-lg-0">
                    <div class="bg-light p-4 rounded-4">
                        <div class="row">
                            <div class="col-md-4">
                                <input type="hidden" name="experiencesList[${index}].seqCode" value="" />
                                <div class="mb-3">
                                    <h3>Experience</h3>
                                </div>
                                <div class="text-primary fw-bolder mb-3">
                                    <input type="date" class="form-control d-inline w-auto" name="experiencesList[${index}].startDate" value="" />
                                    ~
                                    <input type="date" class="form-control d-inline w-auto" name="experiencesList[${index}].endDate" value="" />
                                </div>
                                <div class="mb-3">
                                    <input type="text" class="form-control" name="experiencesList[${index}].title" value="" placeholder="Enter Title" />
                                </div>
                                <div>
                                    <textarea class="form-control" rows="3" name="experiencesList[${index}].subContent" placeholder="Enter Sub Content"></textarea>
                                </div>
                            </div>
                            <div class="col-md-8">
                                <textarea class="form-control" rows="10" name="experiencesList[${index}].content" placeholder="Enter Content"></textarea>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    `;
    // 새로운 Form 추가
    container.insertAdjacentHTML('beforeend', newHTML);
}

function delExperiences() {
    const container = document.querySelector('.experience-container');
    const forms = container.querySelectorAll('.experience-form');
    const lastForm = forms[forms.length - 1];
    if (lastForm) {
        const hiddenInput = lastForm.querySelector('input[type="hidden"][name*=".seqCode"]');
        if (hiddenInput && hiddenInput.value) {
            delExperienceCodes.push(hiddenInput.value);
            const delInput = document.getElementById('delExperiencesCode');
            if (delInput) {
                delInput.value = delExperienceCodes.join(',');
            }
        }
        lastForm.remove();
    } else {
        console.warn('No experience form to delete.');
    }
}

function addEducation() {
    const container = document.querySelector('.education-container');
    const index = container.querySelectorAll('.education-form').length;
    const newHTML = `
        <div class="card-body p-1 education-form">
            <div class="row align-items-center gx-5">
                <div class="col text-center text-lg-start mb-4 mb-lg-0">
                    <div class="bg-light p-4 rounded-4">
                        <div class="row">
                            <div class="col-md-4">
                                <input type="hidden" name="educationList[${index}].seqCode" value="" />
                                <div class="mb-3">
                                    <h3>Education</h3>
                                </div>
                                <div class="text-primary fw-bolder mb-3">
                                    <input type="date" class="form-control d-inline w-auto" name="educationList[${index}].startDate" value="" />
                                    ~
                                    <input type="date" class="form-control d-inline w-auto" name="educationList[${index}].endDate" value="" />
                                </div>
                                <div class="mb-3">
                                    <input type="text" class="form-control" name="educationList[${index}].title" value="" placeholder="Enter Title" />
                                </div>
                                <div>
                                    <textarea class="form-control" rows="3" name="educationList[${index}].subContent" placeholder="Enter Sub Content"></textarea>
                                </div>
                            </div>
                            <div class="col-md-8">
                                <textarea class="form-control" rows="10" name="educationList[${index}].content" placeholder="Enter Content"></textarea>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    `;
    // 새로운 Form 추가
    container.insertAdjacentHTML('beforeend', newHTML);
}

function delEducation() {
    const container = document.querySelector('.education-container');
    const forms = container.querySelectorAll('.education-form');
    const lastForm = forms[forms.length - 1];
    if (lastForm) {
        const hiddenInput = lastForm.querySelector('input[type="hidden"][name*=".seqCode"]');
        if (hiddenInput && hiddenInput.value) {
            delEducationCode.push(hiddenInput.value);
            const delInput = document.getElementById('delEducationCode');
            if (delInput) {
                delInput.value = delEducationCode.join(',');
            }
        }
        lastForm.remove();
    } else {
        console.warn('No experience form to delete.');
    }
}

function addSkill() {
    const container = document.querySelector('.skill-container');
    const index = container.querySelectorAll('.skill-form').length;
    const newHTML = `
    <div class="skill-form" >
        <div class="row mb-4 mb-md-1">
            <div class="d-flex align-items-center bg-light rounded-4 p-3 h-100">
                <input type="text" class="form-control" name="skillList[${index}].title" value="" placeholder="Enter Title" />
            </div>
        </div>
    </div>
    `;
    // 새로운 Form 추가
    container.insertAdjacentHTML('beforeend', newHTML);
}

function delSkill() {
    const container = document.querySelector('.skill-container');
    const forms = container.querySelectorAll('.skill-form');
    const lastForm = forms[forms.length - 1];
    if (lastForm) {
        const hiddenInput = lastForm.querySelector('input[type="hidden"][name*=".seqCode"]');
        if (hiddenInput && hiddenInput.value) {
            delSkillCode.push(hiddenInput.value);
            const delInput = document.getElementById('delSkillCode');
            if (delInput) {
                delInput.value = delSkillCode.join(',');
            }
        }
        lastForm.remove();
    } else {
        console.warn('No experience form to delete.');
    }
}

function addLanguages() {
    const container = document.querySelector('.languages-container');
    const index = container.querySelectorAll('.languages-form').length;
    const newHTML = `
    <div class="languagesList-form" >
        <div class="row mb-4 mb-md-1">
            <div class="d-flex align-items-center bg-light rounded-4 p-3 h-100">
                <input type="text" class="form-control" name="languagesList[${index}].title" value="" placeholder="Enter Title" />
            </div>
        </div>
    </div>
    `;
    // 새로운 Form 추가
    container.insertAdjacentHTML('beforeend', newHTML);
}

function delLanguages() {
    const container = document.querySelector('.languages-container');
    const forms = container.querySelectorAll('.languages-form');
    const lastForm = forms[forms.length - 1];
    if (lastForm) {
        const hiddenInput = lastForm.querySelector('input[type="hidden"][name*=".seqCode"]');
        if (hiddenInput && hiddenInput.value) {
            delLanguagesCode.push(hiddenInput.value);
            const delInput = document.getElementById('delLanguagesCode');
            if (delInput) {
                delInput.value = delLanguagesCode.join(',');
            }
        }
        lastForm.remove();
    } else {
        console.warn('No experience form to delete.');
    }
}