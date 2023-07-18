/**
 * 
 */
var otherCheckbox;
var isValid=false;
var emailCheck=false;

$(function(){
	$("#check-all").click(checkAllClicked);//전체동의
	otherCheckbox=$(".check-box :checkbox").not("#check-all");//나머지 체크박스들
	otherCheckbox.click(otherCheckboxClicked)
	$('.input-wrap input').blur(regExpCheck);
	$(".input-wrap:eq(0)>input").keyup(emailKeyup)
	$("form").on("change", vaildCheck) //폼태그 안의 내용이 바뀔때마다 회원가입버튼 활성화가능한지 체크
	$("form").on("blur", vaildCheck)
	$(".sub-btn>button").submit(successAlert)
	
	
});

function successAlert() {
	alert("회원가입이 완료되었습니다.")
	
}


//이메일,패스워드,닉네임 체크
//인풋태그의 name을 체크해서 각각의 상황의 정규식 체크
function regExpCheck(){
	var target= $(this).attr('name');
	var regExp;
	var in_txt=$(this).val().trim();
	var msg;
	var targetMsg=$(this).parents(".input-wrap").find(".msg");
    

	
	if(target=="email"){
		regExp =  /^[\da-zA-Z]([-_.]?[0-9a-zA-Z])*@[\da-zA-Z]([-_.]?[\da-zA-Z])*\.[a-zA-Z.]{3,5}$/i;
	}else if(target=="password"){
		regExp =  /^.*(?=^.{8,16}$)(?=.*\d)(?=.*[a-zA-Z]).*$/g;
	}else if(target=="nickName"){
		regExp = /^[a-zA-Z0-9가-힣]{2,15}$/;
	}
	
	
	
	if($(this).attr('name')!=="passCheck" && $(this).prop('type') !== "checkbox") {
		if(in_txt==""){
			msg=`${$(this).attr('placeholder')} 입력되지 않았습니다.`;
			targetMsg.css("color","red");
		}else if(regExp.test(in_txt)){
			msg=`${$(this).attr('placeholder')} 형식이 맞습니다.`;
			targetMsg.css("color","green");
		}else{
			msg=`${$(this).attr('placeholder')} 형식이 올바르지 않습니다..`;
			targetMsg.css("color","red");
		}
		
		targetMsg.text(msg).show();
	}
	
	if($(this).attr('name')=="passCheck"){
		var pass=$('.input-wrap>input[name="password"]').val();
		if(in_txt==""){
			msg="비밀번호가 입력되지 않았습니다. 다시 입력해주세요.";
			targetMsg.css("color","red");
		}else if(in_txt==pass){
			msg="비밀번호가 일치합니다."
			targetMsg.css("color","green");
			
		}else{
			msg="비밀번호가 일치하지 않습니다."
			targetMsg.css("color","red");
		}
		targetMsg.text(msg).show();
	}

}

//////회원가입버튼 활성화 가능 여부 확인

function vaildCheck() {
	var regExpEmail=/^[\da-zA-Z]([-_.]?[0-9a-zA-Z])*@[\da-zA-Z]([-_.]?[\da-zA-Z])*\.[a-zA-Z]{2,4}$/i;
	var regExpPass= /^.*(?=^.{8,16}$)(?=.*\d)(?=.*[a-zA-Z]).*$/g;
	var regExpNickName=/^[a-zA-Z0-9가-힣]{2,15}$/;
	
	var check= regExpEmail.test($(".input-wrap:eq(0)>input").val())
	 && regExpPass.test($(".input-wrap:eq(1)>input").val()) 
	 && $(".input-wrap:eq(1)>input").val()==$(".input-wrap:eq(2)>input").val()
	  && regExpNickName.test($(".input-wrap:eq(3)>input").val());
	
	//입력완료 후 수정시에도 유효성검사 재실행하여 틀린 값이 들어가지않도록 함
	
	console.log("동의>>>>>>>"+$("#check-all").is(":checked")+"항목>>>>>"+check)
	//is(":checked")는 체크되어있는지 확인
	//$("#check-all")는 otherCheckboxClicked 펑션으로
	// 모든 항목이 체크되어있으면 무조건 체크되기때문에 동의 체크되었는지 확인가능
	
	if($("#check-all").is(":checked") && check && emailCheck){
		isValid = true; //모든 항목이 유효성검사 완료, 동의체크가 되어있어야 true
	}else {
		isValid = false; //아닐시 false
	}
	console.log(isValid)
	$(".sub-btn button").prop('disabled',!isValid) 
	//prop()으로 disabled 속성 변경
	//회원가입버튼의 disabled은 true일 경우 버튼 비활성화이므로 !으로 반대로 부여
	//isvValid가 true일 경우 버튼 활성화
	
}


///////////////// 약관동의 체크박스 ///////////////////////

function checkAllClicked(){
	
	var status=$(this).is(":checked");
	if(status==true){
		//모든 체크박스 체크상태로 변경
		otherCheckbox.prop("checked",true);
	}else{
		//모든 체크박스 체크상태 해제
		otherCheckbox.prop("checked",false);
	}
	
}

function otherCheckboxClicked(){
	//체크박스의 체크 여부 확인 가능 true or false
	var status=$(this).is(":checked");
	if(status==false){
		$("#check-all").prop("checked",false);
	}else{
		//체크올을 제외한 나머지중 체크상태인 박스의 개수
		var checkedEa=$(".checkbox :checked").not("#check-all").length;
		console.log("체크된 박스 개수: "+checkedEa);
		if(checkedEa==otherCheckbox.length)$("#check-all").prop("checked",true);
		
	}
}

///////////////////이메일체크////////////////////////////


function emailKeyup(){
	var in_email=$(this).val().trim();
		if (in_email.length<10)return;
	//서버-DB로 접근
	var target=$(this).parents(".input-wrap").find(".msg");
	var token = $("meta[name='_csrf']").attr("content");
	var header = $("meta[name='_csrf_header']").attr("content");
	
	 $.ajax({
		 beforeSend:function(xhr) {//csrf적용시 
			xhr.setRequestHeader(header, token); 
		 },	
		url:"/user/email-check",
		type:"post",
		data:{email:in_email},
		//비동기요청이 정상적으로 처리되면 success 함수가 실행함
		success: function(result){
			if(result==true){
				target.text("사용 가능한 이메일입니다.").show();
				target.css("color","green");
				emailCheck=result;
			}else {
				emailCheck=result;
				target.text("사용할 수 없는 이메일입니다.").show();
				target.css("color","red");
			}
			
		}
	});
}