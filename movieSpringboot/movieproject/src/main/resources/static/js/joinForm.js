const joinForm = document.getElementById("joinForm");

const userNameInput = joinForm.username;
const usernameFeed = document.getElementById("usernameFeed")
let  idFlag = false;

const password = joinForm.password;
const passwordFeed = document.getElementById("passwordFeed")
let pwdFlag = false;

const joinPwdC = joinForm.joinPwdC
const passwordCFeed = document.getElementById("passwordCFeed")
let pwdCFlag =false;

const nameInput =joinForm.name;
let nameFlag = false;

const email = joinForm.email;
const emailFeed = document.getElementById("emailFeed");
emailFlag= false;

const subBtn = joinForm.sbmit;

const moiveGenreList = document.getElementById("movieGenreList");
const genreList=['드라마','판타지','서부','공포','로맨스','모험','스릴러','느와르','컬트','다큐멘터리','코미디','가족','미스터리','전쟁','애니메이션','범죄','뮤지컬','SF','액션','무협','에로','서스펜스',
'서사','블랙코미디','실험','영화카툰','영화음악','영화패러디포스터']

let checkList=[];
let rowT=null;
const choiceGenre = joinForm.choiceGenre;
window.addEventListener('load', (e)=>{
    // <label class="form-check-label" for="flexCheckDefault">Default checkbox<input class="form-check-input" type="checkbox" value="" id="flexCheckDefault"></label>
    for(let i=0; i<genreList.length; i++){
        const m =genreList[i];
        if(i % 4 ==0){
            rowT = document.createElement("div")
            rowT.id=i,"_row";
            rowT.classList.add("row")
            moiveGenreList.appendChild(rowT);

        }
        const colT = document.createElement("div");
        colT.classList.add("col")
        colT.classList.add("list-group-item")
        const labelT = document.createElement("label");
        labelT.classList.add("form-check-label")
        labelT.setAttribute("for", m,"_Id");
        labelT.innerText=m;
        const inputT = document.createElement("input")
        inputT.type="checkbox";
        inputT.name="genre"
        inputT.value=m;
        inputT.id=m,"_id";
        inputT.hidden=true;

        labelT.appendChild(inputT);
        colT.appendChild(labelT);
        rowT.appendChild(colT);

        colT.addEventListener("change",(e)=>{
            if(inputT.checked){
                colT.classList.add("active")
                checkList.push(inputT.value)    
            }else{
                colT.classList.remove("active")
                checkList.splice(checkList.indexOf(inputT.value),1)
            }
            joinForm.favoritegenre.value = checkList
        })
    }
})

userNameInput.addEventListener('blur',async (e)=>{
    const value = e.target.value;

    const res = await fetch('/checkUsername',{
        method:'post',
        headers:{
            'Content-Type':'application/json'
        },
        body:JSON.stringify({
            "username" : value
        })

    })
    const result = await res.json();
    if(!result.result && value.length >0){
        //사용가능 username
        userNameInput.classList.remove("is-invalid")
        userNameInput.classList.add("is-valid")
        usernameFeed.classList.remove("invalid-feedback");
        usernameFeed.classList.add("valid-feedback");
        usernameFeed.innerText="사용가능한 아이디"
        idFlag=true;
    }else{
        //사용 불가능 username이다.
        userNameInput.classList.remove("is-valid")
        userNameInput.classList.add("is-invalid")
        usernameFeed.classList.remove("valid-feedback");
        usernameFeed.classList.add("invalid-feedback");
        usernameFeed.innerText="사용 불가능한 아이디"
        idFlag=false;
    }
})

email.addEventListener('blur',async (e)=>{
    const value = e.target.value;

    const res = await fetch('/checkEmail',{
        method:'post',
        headers:{
            'Content-Type':'application/json'
        },
        body:JSON.stringify({
            "email" : value
        })

    })
    const result = await res.json();
    if(!result.result && value.length >0){
        //사용가능 email
        email.classList.remove("is-invalid")
        email.classList.add("is-valid")
        emailFeed.classList.remove("invalid-feedback");
        emailFeed.classList.add("valid-feedback");
        emailFeed.innerText="사용가능한 이메일"
        emailFlag=true;
    }else{
        //사용 불가능 email이다.
        email.classList.remove("is-valid")
        email.classList.add("is-invalid")
        emailFeed.classList.remove("valid-feedback");
        emailFeed.classList.add("invalid-feedback");
        emailFeed.innerText="이미 가입된 이메일입니다."
        emailFlag=false;
    }
})

password.addEventListener("blur",(e)=>{
    const value = e.target.value;
    const regexr = /[A-Za-z0-9]/gi; //특수 문자가 있는지 확인하는 거임 
    const specialWord = value.replaceAll(regexr,"");
    if(value.length >=8 && value.length<=15){
        if(specialWord.length >0){
			pwdFlag = true;
		}else{
			pwdFlag =false;
		}
    }else{
        pwdFlag=false;
    }


    if(pwdFlag){
        password.classList.remove("is-invalid")
        password.classList.add("is-valid")
        passwordFeed.classList.remove("invalid-feedback");
        passwordFeed.classList.add("valid-feedback");
        passwordFeed.innerText="사용가능한 패스워드입니다."
    }else{
        password.classList.add("is-invalid")
        password.classList.remove("is-valid")
        passwordFeed.classList.add("invalid-feedback");
        passwordFeed.classList.remove("valid-feedback");
        passwordFeed.innerText="사용 불가능한 비밀번호입니다.(특수문자를 포함한 8~15)"
    }
})

joinPwdC.addEventListener("blur", (e)=>{
	const value = e.target.value;
	if(value.length>0){
		if(value == password.value){
			pwdCFlag = true;
		}else{
			pwdCFlag = false;
		}
	}else{
		pwdCFlag = false;
	}
	
	passwordCFeed.innerHTML = "";
	if(pwdCFlag){
		joinPwdC.classList.remove("is-invalid")
		joinPwdC.classList.add("is-valid")
		passwordCFeed.classList.add("valid-feedback")
		passwordCFeed.classList.remove("invalid-feedback")
		passwordCFeed.innerText="확인 완료!";
	}else{
		joinPwdC.classList.remove("is-valid")
		joinPwdC.classList.add("is-invalid")
		passwordCFeed.classList.add("invalid-feedback")
		passwordCFeed.classList.remove("valid-feedback")
		passwordCFeed.innerText="비밀번호를 다시 확인해주세요!";
	}
	
});

nameInput.addEventListener("blur", (e)=>{
	if(e.target.value.length ==0){
		nameFlag=false;
	}else{
		nameFlag=true;
	}
})



joinForm.addEventListener("submit",(e)=>{
	if(idFlag && pwdFlag && pwdCFlag && nameFlag && emailFlag){
	}else{
		e.preventDefault();
	}
})
setInterval(() =>{
	if(idFlag && pwdFlag && pwdCFlag && nameFlag && emailFlag){
		subBtn.classList.remove("disabled");
	}else{
		subBtn.classList.add("disabled");
		
	}
}, 1000);

