/**
 * 
 */
var items;
var target_idx=0;

var delay=3000;
var timmer;
var bullets;

document.addEventListener("visibilitychange", function() {
	console.log(document.visibilityState);
  if(document.visibilityState=="hidden"){
	  stop();
  }else if(document.visibilityState=="visible"){
	  auto();
  }
});


$(function(){
	items=$("#visual .item");
	bullets=$("#visual .bullet");
	move(0);//초기화
	
	auto();//타이머를 이용한 자동실행
	
	//$("#visual .wrap").hover(stop, auto);
	
	$(".btn-wrap .btn").hover(stop, auto);
	$(".bullet-wrap .bullet").hover(stop, auto);
	
	$(".bullet-wrap .bullet").click(bulletClicked);
	$(".auto-wrap button span").click(playStopClicked);
});

function playStopClicked(){
	$(this).hide();
	$(this).siblings().show();
	var playOrStop=$(this).index(); //0:play 1:stop
	if(playOrStop==0)auto();
	if(playOrStop==1)stop();
}

function auto(){
	stop();
	timmer=setTimeout(start, delay);
	//console.log("타이머 시작");
}
function stop(){
	clearTimeout(timmer);
	//console.log("타이머 멈춤");
}
function start(){
	move(1);
	auto();
}

//클릭한 인덱스번호가 타켓이 되도록
function bulletClicked(){
	var bi=$(this).index();
	target_idx=bi;
	move(0);
}
//이미지를 좌(1)또는 우(-1)로 이동하는 함수
function move(dir){
	//var target_idx=$(".item.target").index();
	target_idx=(target_idx+dir) % items.length;
	var next=(target_idx+1) % items.length;
	var prev=(target_idx-1) % items.length;
	items.removeClass("target next prev");
	items.eq(target_idx).addClass("target");
	items.eq(next).addClass("next");
	items.eq(prev).addClass("prev");
	
	bullets.removeClass("target");
	bullets.eq(target_idx).addClass("target");
	
	$(".txt .target").text($(".item.target").index()+1);
}

