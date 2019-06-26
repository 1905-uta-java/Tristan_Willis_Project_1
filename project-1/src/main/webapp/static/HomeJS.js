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

document.getElementById("account").addEventListener("click", function(){
	window.location.href = "/project-1/static/account.html";
})
document.getElementById("createReport").addEventListener("click", function(){
	window.location.href = "/project-1/static/newReport.html";
})
document.getElementById("pendingReport").addEventListener("click", function(){
	window.location.href = "/project-1/static/viewPending.html";
})
document.getElementById("pastReport").addEventListener("click", function(){
	window.location.href = "/project-1/static/viewPast.html";
})
document.getElementById("subordinate").addEventListener("click", function(){
	window.location.href = "/project-1/static/viewSubordinates.html";
})