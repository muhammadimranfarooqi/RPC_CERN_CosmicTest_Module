var xmlhttp1;

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

var reqDate = document.getElementById('reqDate');
var reqDateValue = reqDate.value;

if(reqDateValue==null || reqDateValue=="")
{
	var url="validateFile.jsp";
	url=url+"?flag=3";
	url=url+"&sid="+Math.random();
	//alert(url);
	xmlhttp1.onreadystatechange=fileMessage;
	xmlhttp1.open("GET",url,true);
	xmlhttp1.send(null);
	//alert("Please select file to upload in the database.",'titlebar=no');
	reqDate.focus();
	return false;

}

var series = document.getElementById('series');
var seriesvalue = series.value;

if(seriesvalue==null || seriesvalue=="")
{
	var url="validateFile.jsp";
	url=url+"?flag=6";
	url=url+"&sid="+Math.random();
	//alert(url);
	xmlhttp1.onreadystatechange=fileMessage;
	xmlhttp1.open("GET",url,true);
	xmlhttp1.send(null);
	//alert("Please select file to upload in the database.",'titlebar=no');
	series.focus();
	return false;

}

if (parseFloat(seriesvalue)==seriesvalue)
	;
	else
	{
	var url="validateFile.jsp";
	url=url+"?flag=7";
	url=url+"&sid="+Math.random();
	//alert(url);
	xmlhttp1.onreadystatechange=fileMessage;
	xmlhttp1.open("GET",url,true);
	xmlhttp1.send(null);
	//alert("Please select file to upload in the database.",'titlebar=no');
	series.focus();
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


