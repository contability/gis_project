var agt = navigator.userAgent.toLowerCase();
is_window = (agt.indexOf('windows') != -1);
function HttpClient() {
	try {
		var objRequest = new XMLHttpRequest();
		// var objRequest = new XDomainRequest();
		if (null != objRequest)
			this.m_objHttpRequest = objRequest;
	} catch (e) {
		try {
			var objRequest = new ActiveXObject("Msxml2.XMLHTTP");
			if (null != objRequest)
				this.m_objHttpRequest = objRequest;
		} catch (e) {
			try {
				var objRequest = new ActiveXObject("Microsoft.XMLHTTP");
				if (null != objRequest)
					this.m_objHttpRequest = objRequest;
			} catch (e) {
				throw new Error("HttpRequest not supported");
			}
		}
	}
}

HttpClient.prototype.send = function(strURL, objData, bAsync,
		handleASyncHttpResponse) {
	if (bAsync) {
		this.m_objHttpRequest.onreadystatechange = handleASyncHttpResponse;
	} else {
		this.m_objHttpRequest.onreadystatechange = null;
	}
	
	try{
	this.m_objHttpRequest.open("POST", strURL, bAsync);
	this.m_objHttpRequest.setRequestHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
	//this.m_objHttpRequest.setRequestHeader("Content-Encoding", "gzip");
		this.m_objHttpRequest.send(objData);
	}catch(send_e){
		if(window.console){
 		   window.console.log(send_e);
 	   }
	}
	
	

	if (!bAsync) {
		if (4 == this.m_objHttpRequest.readyState) {
			if (200 == this.m_objHttpRequest.status) {
				return this.m_objHttpRequest.responseText;
			} else {
				throw new Error(this.m_objHttpRequest.status);
			}
		}
		return this._handleSyncHttpResponse();
	}
};

function ClipReportExePrintCheck(func_exec){
	var hostName = "";
	hostName = "127.0.0.1";
	var urlArray =["http://"+hostName+":11000/version",
	               "http://"+hostName+":22000/version",
	               "http://"+hostName+":33000/version"];
	
	if("https:" == location.protocol.toLocaleLowerCase()){
		urlArray = ["https://"+hostName+":11443/version",
		            "https://"+hostName+":22443/version",
		            "https://"+hostName+":33443/version"];
	}
	
	
	if (window.XDomainRequest) {
		var xdr0 = new XDomainRequest();
        if (xdr0) {
           try{
        	   xdr0.onload = function () {
        		  var object = (new Function("return " +  xdr0.responseText))();
	 			  func_exec(object.version);
               };
               xdr0.onerror = function () {
               	
               };
               xdr0.open('POST', urlArray[0]);
               xdr0.send("");
           }catch(send_e){
        	   if(window.console){
        		   window.console.log(send_e);
        	   }
           }
        	
        }
        var xdr1 = new XDomainRequest();
        if (xdr1) {
	    	 try{
	    		  xdr1.onload = function () {
	    			 var object = (new Function("return " +  xdr1.responseText))();
	 				 func_exec(object.version);
	              };
	              xdr1.onerror = function () {
	              };
	              xdr1.open('POST', urlArray[1]);
	              xdr1.send("");
	    	 }catch(send_e){
	    		   if(window.console){
	        		   window.console.log(send_e);
	        	   }
	         }
          
        }
        var xdr2 = new XDomainRequest();
        if (xdr2) {
        	try{
        		 xdr2.onload = function () {
        			var object = (new Function("return " +  xdr2.responseText))();
 					func_exec(object.version);
                 };
                 xdr2.onerror = function () {
                 };
                 xdr2.open('POST', urlArray[2]);
                 xdr2.send("");
        	}catch(send_e){
        		   if(window.console){
            		   window.console.log(send_e);
            	   }
        	}
        }
    }
	else{
		var objHttp1 = new HttpClient();
		objHttp1.send(urlArray[0], '', true, function(){
			if (4 == this.readyState) {
				if (200 == this.status) {
					var object = (new Function("return " + this.responseText))();
					func_exec(object.version);
				} 
			}
		});
		var objHttp2 = new HttpClient();
		objHttp2.send(urlArray[1], '', true, function(){
			if (4 == this.readyState) {
				if (200 == this.status) {
					var object = (new Function("return " + this.responseText))();
					func_exec(object.version);
				} 
			}
		});
		var objHttp3 = new HttpClient();
		objHttp3.send(urlArray[2], '', true, function(){
			if (4 == this.readyState) {
				if (200 == this.status) {
					var object = (new Function("return " + this.responseText))();
					func_exec(object.version);
				}
			}
		});
	}
} 		



