/*
 * 
 */
let token = sessionStorage.getItem("token");
if(!token){
    window.location.href = "http://localhost:8080/project-1/static/index.html";
}
else {
	let tokenArr = token.split(":");
	if(tokenArr.length==2){
		
	}
	else{
	    window.location.href = "http://localhost:8080/project-1/static/index.html";
	}
}