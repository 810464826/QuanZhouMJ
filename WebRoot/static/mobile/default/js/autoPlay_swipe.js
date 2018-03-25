	var touchEvent={
		
		/*单次触摸事件*/
		tap:function(element,fn){
			var startTx, startTy;
			element.addEventListener('touchstart',function(e){
			  var touches = e.touches[0];
			  startTx = touches.clientX;
			  startTy = touches.clientY;
			}, false );
			
			element.addEventListener('touchend',function(e){
			  var touches = e.changedTouches[0],
			  endTx = touches.clientX,
			  endTy = touches.clientY;
			  // 在部分设备上 touch 事件比较灵敏，导致按下和松开手指时的事件坐标会出现一点点变化
			  if( Math.abs(startTx - endTx) < 6 && Math.abs(startTy - endTy) < 6 ){
				fn();
			  }
			}, false );
		},
		
		/*两次触摸事件*/
		doubleTap:function(element,fn){
			var isTouchEnd = false,
			lastTime = 0,
			lastTx = null,
			lastTy = null,
			firstTouchEnd = true,
			body = document.body,
			dTapTimer, startTx, startTy, startTime;
			element.addEventListener( 'touchstart', function(e){
			  if( dTapTimer ){
				clearTimeout( dTapTimer );
				dTapTimer = null;
			  }
			  var touches = e.touches[0];
			  startTx = touches.clientX;
			  startTy = touches.clientY;   
			}, false );
			element.addEventListener( 'touchend',function(e){
			  var touches = e.changedTouches[0],
			  endTx = touches.clientX,
			  endTy = touches.clientY,
			  now = Date.now(),
			  duration = now - lastTime;
			  // 首先要确保能触发单次的 tap 事件
			  if( Math.abs(startTx - endTx) < 6 && Math.abs(startTx - endTx) < 6 ){
				// 两次 tap 的间隔确保在 500 毫秒以内
				if(duration < 301 ){
				  // 本次的 tap 位置和上一次的 tap 的位置允许一定范围内的误差
				  if( lastTx !== null &&
					Math.abs(lastTx - endTx) < 45 &&
					Math.abs(lastTy - endTy) < 45 ){
					  firstTouchEnd = true;
					  lastTx = lastTy = null;
					  fn();
					}
				  }
				  else{
					lastTx = endTx;
					lastTy = endTy;
				  }
				}
				else{
				  firstTouchEnd = true;
				  lastTx = lastTy = null;
				}
				lastTime = now;
			  }, false );
			  // 在 iOS 的 safari 上手指敲击屏幕的速度过快，
			  // 有一定的几率会导致第二次不会响应 touchstart 和 touchend 事件
			  // 同时手指长时间的touch不会触发click
			  if(~navigator.userAgent.toLowerCase().indexOf('iphone os')){
				body.addEventListener( 'touchstart', function(e){
				  startTime = Date.now();
				}, true );
				body.addEventListener( 'touchend', function(e){
				  var noLongTap = Date.now() - startTime < 501;
				  if(firstTouchEnd ){
					firstTouchEnd = false;
					if( noLongTap && e.target === element ){
					  dTapTimer = setTimeout(function(){
						firstTouchEnd = true;
						lastTx = lastTy = null;
						fn();
					  },400);
					}
				  }
				  else{
					firstTouchEnd = true;
				  }
				}, true );
				// iOS 上手指多次敲击屏幕时的速度过快不会触发 click 事件
				element.addEventListener( 'click', function( e ){
				  if(dTapTimer ){
					clearTimeout( dTapTimer );
					dTapTimer = null;
					firstTouchEnd = true;
				  }
				}, false );
			}	
		},
		
		/*长按事件*/
		longTap:function(element,fn){
			var startTx, startTy, lTapTimer;
			element.addEventListener( 'touchstart', function( e ){
			  if( lTapTimer ){
				clearTimeout( lTapTimer );
				lTapTimer = null;
			  }
			  var touches = e.touches[0];
			  startTx = touches.clientX;
			  startTy = touches.clientY;
			  lTapTimer = setTimeout(function(){
				fn();
			  }, 1000 );
			  e.preventDefault();
			}, false );
			element.addEventListener( 'touchmove', function( e ){
			  var touches = e.touches[0],
				endTx = touches.clientX,
				endTy = touches.clientY;
			  if( lTapTimer && (Math.abs(endTx - startTx) > 5 || Math.abs(endTy - startTy) > 5) ){
				clearTimeout( lTapTimer );
				lTapTimer = null;
			  }
			}, false );
			element.addEventListener( 'touchend', function( e ){
			  if( lTapTimer ){
				clearTimeout( lTapTimer );
				lTapTimer = null;
			  }
			}, false );	
		},
		
		/*滑屏事件*/
		swipe:function(element,fn){
			var isTouchMove, startTx, startTy;			
			element.addEventListener( 'touchstart', function( e ){
			  var touches = e.touches[0];
			  startTx = touches.clientX;
			  startTy = touches.clientY;
			  isTouchMove = false;
			}, false );
			element.addEventListener( 'touchmove', function( e ){
			  isTouchMove = true;
			  e.preventDefault();
			}, false );
			element.addEventListener( 'touchend', function( e ){
			  if( !isTouchMove ){
				return;
			  }
			  var touches = e.changedTouches[0],
				endTx = touches.clientX,
				endTy = touches.clientY,
				distanceX = startTx - endTx
				distanceY = startTy - endTy,				
				isSwipe = false;
				e.dx = distanceX;
				e.dy = distanceY; 
			  if( Math.abs(distanceX)>20||Math.abs(distanceY)>20 ){
				fn(e);
			  }
			}, false );	
		},
		
		/*向上滑动事件*/
		swipeUp:function(element,fn){
			var isTouchMove, startTx, startTy;
			element.addEventListener( 'touchstart', function( e ){
			  var touches = e.touches[0];
			  startTx = touches.clientX;
			  startTy = touches.clientY;
			  isTouchMove = false;
			}, false );
			element.addEventListener( 'touchmove', function( e ){
			  isTouchMove = true;
			  e.preventDefault();
			}, false );
			element.addEventListener( 'touchend', function( e ){
			  if( !isTouchMove ){
				return;
			  }
			  var touches = e.changedTouches[0],
				endTx = touches.clientX,
				endTy = touches.clientY,
				distanceX = startTx - endTx
				distanceY = startTy - endTy,
				isSwipe = false;
			  if( Math.abs(distanceX) < Math.abs(distanceY) ){
				  if( distanceY > 20 ){
					  fn();       
					  isSwipe = true;
				  }
			  }
			}, false );	
		},
		
		/*向下滑动事件*/
		swipeDown:function(element,fn){
			var isTouchMove, startTx, startTy;
			element.addEventListener( 'touchstart', function( e ){
			  var touches = e.touches[0];
			  startTx = touches.clientX;
			  startTy = touches.clientY;
			  isTouchMove = false;
			}, false );
			element.addEventListener( 'touchmove', function( e ){
			  isTouchMove = true;
			  e.preventDefault();
			}, false );
			element.addEventListener( 'touchend', function( e ){
			  if( !isTouchMove ){
				return;
			  }
			  var touches = e.changedTouches[0],
				endTx = touches.clientX,
				endTy = touches.clientY,
				distanceX = startTx - endTx
				distanceY = startTy - endTy,
				isSwipe = false;
			  if( Math.abs(distanceX) < Math.abs(distanceY) ){
				  if( distanceY < -20  ){
					  fn();       
					  isSwipe = true;
				  }
			  }
			}, false );	
		},
		
		/*向左滑动事件*/
		swipeLeft:function(element,fn){
			var isTouchMove, startTx, startTy;
			element.addEventListener( 'touchstart', function( e ){
			  var touches = e.touches[0];
			  startTx = touches.clientX;
			  startTy = touches.clientY;
			  isTouchMove = false;
			}, false );
			element.addEventListener( 'touchmove', function( e ){
			  isTouchMove = true;
			  e.preventDefault();
			}, false );
			element.addEventListener( 'touchend', function( e ){
			  if( !isTouchMove ){
				return;
			  }
			  var touches = e.changedTouches[0],
				endTx = touches.clientX,
				endTy = touches.clientY,
				 distanceX = startTx - endTx				
				distanceY = startTy - endTy,
				isSwipe = false;
				e.dx = distanceX;
				e.dy = distanceY; 
			  if( Math.abs(distanceX) >= Math.abs(distanceY) ){
				  if( distanceX > 20  ){
					  fn(e);       
					  isSwipe = true;
				  }
			  }
			}, false );	
		},
		
		/*向右滑动事件*/
		swipeRight:function(element,fn){
			var isTouchMove, startTx, startTy;
			element.addEventListener( 'touchstart', function( e ){
			  var touches = e.touches[0];
			  startTx = touches.clientX;
			  startTy = touches.clientY;
			  isTouchMove = false;
			}, false );
			element.addEventListener( 'touchmove', function( e ){
			  isTouchMove = true;
			  e.preventDefault();
			}, false );
			element.addEventListener( 'touchend', function( e ){
			  if( !isTouchMove ){
				return;
			  }
			  var touches = e.changedTouches[0],
				endTx = touches.clientX,
				endTy = touches.clientY,
				distanceX = startTx - endTx
				distanceY = startTy - endTy,
				isSwipe = false;
				e.dx = distanceX;
				e.dy = distanceY; 
			  if( Math.abs(distanceX) >= Math.abs(distanceY) ){
				  if( distanceX < -20  ){
					  fn(e);       
					  isSwipe = true;
				  }
			  }
			}, false );	
		}
		
	}
/***********定义滑动事件结束****************/
	
	var showWidth = $(".auto-show-box").width();
	var showHeight = $(".auto-show-box").height();
	var playNum = $(".auto-content-box>div").length;
	$(".auto-content-box").height(showHeight).width(playNum*showWidth).css({"position":"relative","left":-showWidth});
	$(".auto-content-box>div").height(showHeight).width(showWidth).css({"float":"left","text-align":"center"});
	
	var moveNum = 1;
	var dalayTime = null;
	var intervalTime = 3000;
	var autoPlay = setInterval(function(){ auto_paly(); },intervalTime)	
	
	function auto_paly(){//自动轮播事件
		if(moveNum<(playNum-1)){
			moveNum++;
			$(".auto-content-box").animate({left:-moveNum*showWidth},"slow");
		}else{
			moveNum = 1;
			$(".auto-content-box").css("left",-moveNum*showWidth);
			$(".auto-content-box").animate({left:-(moveNum+1)*showWidth},"slow");
			moveNum++;
		}
	}
	
	touchEvent.swipeLeft($(".auto-show-box")[0],function(){//左滑执行函数
		clearInterval(autoPlay);
		moveNum++;
		swipeEvent(playNum,1,2);
		reset_play();
	});
	
	touchEvent.swipeRight($(".auto-show-box")[0],function(){//右滑执行函数
		clearInterval(autoPlay);
		moveNum--;
		swipeEvent(-1,playNum-2,playNum-3);
		reset_play();
	});
	
	function swipeEvent(limitNum,leftNum,resetNum){//滑动事件执行函数
		if(moveNum == limitNum){//当moveNum值为极限值时
			$(".auto-content-box").css("left",-leftNum*showWidth);
			moveNum = resetNum;//重新定义moveNum值
			$(".auto-content-box").animate({left:-moveNum*showWidth},"fast");
		}else{
			$(".auto-content-box").animate({left:-moveNum*showWidth},"fast");
		}
	}
	
	function reset_play(){//重新运行自动轮播事件
		clearTimeout(dalayTime);//清除已存在的dalayTime事件，避免运行多个dalayTime事件
		dalayTime = setTimeout(function(){//重新定义dalayTime事件
			autoPlay = setInterval(function(){ auto_paly(); },intervalTime);
		},intervalTime/2)
	}
