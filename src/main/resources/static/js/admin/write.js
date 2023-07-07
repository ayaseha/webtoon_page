/**
 * 
 */
$(function(){
	seriesList()
});

 function tempUpload(fileTag){
	 
	 var target=$(fileTag);
	 
	 var img=target[0].files[0];
	 var formData=new FormData();
	 
	 //Controller의 MultipartFile 매개변수와 일치하게 MultipartFile tempImg
	 formData.append("tempImg", img);
	 
	 
	 //*
	 //csrf적용시 
	var token = $("meta[name='_csrf']").attr("content");
	var header = $("meta[name='_csrf_header']").attr("content");
	
	 $.ajax({
		 beforeSend:function(xhr) {//csrf적용시 
			xhr.setRequestHeader(header, token); 
		 },		
		 url:"/admin/series/temp",
		 type:"post",
		 data: formData,
		 contentType:false,
		 processData:false,
		 success: function(map){
			 console.log("이미지경로:"+map);
			 target.parents(".img-wrap").find("label")
			 	.css("background-image", `url(${map.url})`);
			 target.parents(".img-wrap").find(".bucket-key")
			 	.val(map.bucketKey);
			 target.parents(".img-wrap").find(".org-name")
			 	.val(map.orgName);
			 target.parents(".img-wrap").find(".new-name")
			 	.val(map.newName);	
			 
			 var targetIdx=$(fileTag).parents(".img-wrap").index()+1;
			 
			 var tagLength= $("#img-area>.img-wrap").length;
			 console.log(targetIdx+" / "+ tagLength)
			 
			 
			 var  tag=`
				<i class="img-wrap">
					<label>
						<span>+</span>
						<input type="file" onchange="tempUpload(this)">
					</label>
					<input type="hidden" class="bucket-key" name="bucketKey" >
					<input type="hidden" class="org-name" name="orgName" >
					<input type="hidden" class="new-name" name="newName" >
				</i>
				`
				console.log()
			if(targetIdx>tagLength && !target.parents().hasClass("first")){
				spanHide()
				$("#img-area").append(tag);
			 }
			
			
		 }
	 })
	 //*/
 }
 
 function spanHide() {
	var target= $(".img-wrap>label>span")
	
	target.addClass("hide")
 }
 
 function seriesList(){
	 // 시리즈 목록을 셀렉트태그로 넣는 비동기 작업
	$.get(
		"/admin/series/list",//url
		function(resultHtml) { //success
			$("#series").html(resultHtml);
		}
	)
}