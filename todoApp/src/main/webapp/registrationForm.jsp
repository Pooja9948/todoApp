<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
  
  <link rel="stylesheet" type="text/css" href="css/login&registration.css">
</head>
<body class="x">
	<div class="container">
		<div class="row">
		<div class="col-lg-4 abc ">
	    	<form style="padding: 20px;" method="post" action="Registration" name="submit">
	    	<div style="font-size: 250%;">
		    	Create an Account
	    	</div>
	    	<div class="form-group">
				    <label for="name">Enter your Full Name</label>
				    <input type="text" name="name" class="form-control" id="name">
				</div>
		    	<div class="form-group">
				    <label for="email">Enter your email address</label>
				    <input type="email" name="email" class="form-control" id="email" required>
				</div>
		    	<div class="form-group">
				    <label for="pwd">Enter password</label>
				    <input type="password" name="password" class="form-control" id="pwd">
				</div>
		    	<div class="form-group">
				    <label for="pwd">Confirm password</label>
				    <input type="password" name="password" class="form-control" id="pwd">
				</div>
				<div class="form-group">
				    <label for="mobileno">Mobile No</label>
				    <input type="text" name="mobileno" class="form-control" id="mobileno">
				</div>
				<div class="form-group z">
				 By clicking on the "Create an account" button below, you certify that
				 you have read and agree to our<a> terms of use</a> and <a>privacy policy</a>
				</div>
				<div>
					<input type="submit" name="submit" id="submit" value="submit">Create an account
				  </div>
				<div class="form-group">Already have an account?<a href="login.jsp">Sign in.</a>
				</div>
	    	</form>
	    	</div>
		</div>
	</div>
</body>
<script>


	function validateRegistration() {
		var name = document.getElementById("name").value;
		if (name.length < 3) {
			alert("Name is too short");
			return false;
		}
		console.log("Before email");
		var email = document.getElementById("email").value;
		var regEx = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
		if (!regEx.test(email)) {
			alert("Please enter valid emailId")
			return false;
		}

		var password = document.getElementById("password").value;
		if (password.length < 8) {
			alert("Password must be at least 8 characters long");
			return false;
		}

		var contact = document.getElementById("mobilenumber").value;
		if (isNaN(contact)) {
			alert("Invalid contact number");
			return false;
		}
		if (contact.toString().length != 10) {
			alert("Contact number must have 10 digits");
			return false;
		}

		return true;
	}
</script>
</html>