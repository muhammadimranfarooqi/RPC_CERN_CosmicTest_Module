var xmlhttp1;
function numbersonly(e){
		var unicode=e.charCode? e.charCode : e.keyCode
		if (unicode!=8){ //if the key isn't the backspace key (which we should allow)
			if (unicode<48||unicode>57) //if not a number
			return false //disable key press
		}
} 

function Checkfiles()
{
xmlhttp1=GetXmlHttpObject();
if (xmlhttp1==null)
  {
  alert ("Your browser does not support AJAX!");
  return;
  }
 var fup = document.getElementById('file');
var fileName = fup.value;

var testset = document.getElementById('test_set_txt');
var test_set_txt = testset.value;

if(test_set_txt==null || test_set_txt=="")
{
	var url="validateFile.jsp";
	url=url+"?flag=3";
	url=url+"&sid="+Math.random();
	//alert(url);
	xmlhttp1.onreadystatechange=fileMessage;
	xmlhttp1.open("GET",url,true);
	xmlhttp1.send(null);
	//alert("Please select file to upload in the database.",'titlebar=no');
	testset.focus();
	return false;

}


if(fileName==null || fileName=="")
{
	var url="validateFile.jsp";
	url=url+"?flag=1";
	url=url+"&sid="+Math.random();
	//alert(url);
	xmlhttp1.onreadystatechange=fileMessage;
	xmlhttp1.open("GET",url,true);
	xmlhttp1.send(null);
	//alert("Please select file to upload in the database.",'titlebar=no');
	fup.focus();
	return false;

}
else{
var ext = fileName.substring(fileName.lastIndexOf('.') + 1);
if(ext == "txt" || ext == "csv" || ext == "TXT" || ext == "CSV")
{
	document.forms["select_form"].submit();
} 
else
{
	//alert("You can only upload txt or csv file. Please try again.");
	var url="validateFile.jsp";
	url=url+"?flag=2";
	url=url+"&sid="+Math.random();
	xmlhttp1.onreadystatechange=fileMessage;
	xmlhttp1.open("GET",url,true);
	xmlhttp1.send(null);
	fup.focus();
	return false;
}
}
}


function fileMessage()
{
	
if (xmlhttp1.readyState==4)
  {
  document.getElementById("FileDiv").innerHTML=xmlhttp1.responseText;
  }
}

function GetXmlHttpObject()
{
if (window.XMLHttpRequest)
  {
  // code for IE7+, Firefox, Chrome, Opera, Safari
  return new XMLHttpRequest();
  }
if (window.ActiveXObject)
  {
  // code for IE6, IE5
  return new ActiveXObject("Microsoft.XMLHTTP");
  }
return null;
}


