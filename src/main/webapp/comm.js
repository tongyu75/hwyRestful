function allinfo(){

    var ua = navigator.userAgent;

    ua = ua.toLowerCase();

    var match = /(webkit)[ \/]([\w.]+)/.exec(ua) ||

    /(opera)(?:.*version)?[ \/]([\w.]+)/.exec(ua) ||

    /(msie) ([\w.]+)/.exec(ua) ||

    !/compatible/.test(ua) && /(mozilla)(?:.*? rv:([\w.]+))?/.exec(ua) || [];

    //如果需要获取浏览器版本号：match[2]

    switch(match[1]){

    case "msie":

    if (parseInt(match[2]) < 5){ //ie6

    alert("暂时不支持IE10.0及以下版本浏览器，请升级您的浏览器版本！");
    }
    break;

    case "webkit":
    break;

    case "opera": 

    break;

    case "mozilla":

    break;

    default:

    break;

    }

    }
$.extend($.fn.validatebox.defaults.rules, {
		code: {
		  validator: function(value){
		  var rex=/^[0-9]+$/; 
		  var rex2=/^((0\d{2,3})-)(\d{7,8})(-(\d{3,}))?$/;
		  if(rex.test(value)||rex2.test(value))
		  {
		    return true;
		  }else
		  {
		     return false;
		  }
		    
		  },
		  message: '请输入数字'
		}

		});