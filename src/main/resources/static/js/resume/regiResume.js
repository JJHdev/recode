$(document).ready(function(){
   // $('#resumeButton').on('click', function (event) {
     //   event.preventDefault(); // 기본 동작(페이지 이동) 방지
      //  authAnchor('/nextPage'); // authAnchor 호출
    //});


});

function onClickButton(buttonId) {
    switch (buttonId) {
        case "addExperiencesBtn":
            addExperiences()
            break;
        case "delExperiencesBtn":
            delExperiences()
            break;
        case "addEducationBtn":
            break;
        case "delEducationBtn":
            break;
        case "addSkillBtn":
            break;
        case "delSkillBtn":
            break;
        case "addLanguagesBtn":
            break;
        case "delLanguagesBtn":
            break;
        default:
            console.log("Unknown button ID:", buttonId);
            break;
   }
}

function addExperiences(){
    const container = document.querySelector('.experience-container');
    const index = container.querySelectorAll('.experience-form').length;
    const newExperienceHTML = `
        <div class="card-body p-5 experience-form">
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
    container.insertAdjacentHTML('beforeend', newExperienceHTML);
}

function delExperiences(){
    const container = document.querySelector('.experience-container');
    const forms = container.querySelectorAll('.experience-form');

    if(forms.length <= 1){
        return false;
    }

    const lastForm = forms[forms.length - 1];

    if (lastForm) {
        lastForm.remove();
    } else {
        console.warn('No experience form to delete.');
    }
}

function addEducation(){

}
function delEducation(){
    const container = document.querySelector('.education-container');
    const forms = container.querySelectorAll('.education-form');

    if(forms.length <= 1){
        return false;
    }

    const lastForm = forms[forms.length - 1];

    if (lastForm) {
        lastForm.remove();
    } else {
        console.warn('No experience form to delete.');
    }
}

function addSkill(){

}
function delSkill(){

}

function addLanguages(){

}
function delLanguages(){

}


