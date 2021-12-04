<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="stylesheet" href="../../assets/css/reset.css">
<link rel="stylesheet" href="../../assets/css/header.css">
<link rel="stylesheet" href="../../assets/css/fonts.css">
<link rel="stylesheet" href="../../assets/css/join.css">
<link rel="shortcut icon" type="image/x-icon" href="../../favicon.png">
<title>나드리 - 회원가입</title>
</head>
<script src="http://code.jquery.com/jquery-latest.js"></script>
<script>
	var test;
	$(document).ready(function() {
		//아이디체크 url:"api/emailCheck.jsp", "Nadri-frontEnd/main"
		$("#checking").on("click", function() {
			//json 형식으로 데이터 set
			var params = {
				email : $("[name=usersEmail]").val()
			}

			$.ajax({
				url : "emailChk",
				type : "post",
				data : params,
				dataType : "text",
				success : function checkEmail(isExist) {
					console.log("check: " + isExist);
					test = isExist;
					if ("true" == isExist) {
						$(".hide").text("사용할 수 있는 아이디 입니다.")
						$('.hide').css('color', 'blue');
						return true;
					} else {
						$(".hide").text("아이디가 중복됐습니다.")
						$('.hide').css('color', 'red');
						return false;
					}
					return isExist
				},
				error : function(XHR, status, error) {
					console.error(status + " : " + error);
				}
			});
		});

		$("#password").keyup(function checkPW() {
			var reg_pw = /^(?=.*[a-zA-Z!@#$%])(?=.*[0-9]).{6,12}/;
			if (!reg_pw.test($.trim($("[name=usersPassword]").val()))) {
				$('#chkReg').text('영문자+숫자 조합된 6~12자리로 만들어주세요');
				$('#chkReg').css('color', 'red');
				return false;
			} else {
				$('#chkReg').text('사용가능한 비밀번호 입니다.');
				$('#chkReg').css('color', 'blue');
				return true;
			}
		});

		$("#chk_password").keyup(function checkPW2() {
			if ($('#password').val() != $('#chk_password').val()) {
				$('#chkPw').text('입력한 비밀번호가 일치하지 않습니다');
				$('#chkPw').css('color', 'red');
				return false;
			} else {
				$('#chkPw').text('비밀번호가 일치합니다');
				$('#chkPw').css('color', 'blue');
				return true;
			}
		});

	}); //documenet.ready
	
	
	function checkEmail() {
		if ("true" == test) {
			return true;
		} else {
			return false;
		}
	}

	function checkPW() {
		var reg_pw = /^(?=.*[a-zA-Z!@#$%])(?=.*[0-9]).{6,12}/;
		if (!reg_pw.test($.trim($("[name=usersPassword]").val()))) {
			$('#chkReg').text('영문자+숫자 조합된 6~12자리로 만들어주세요');
			$('#chkReg').css('color', 'red');
			return false;
		} else {
			$('#chkReg').text('사용가능한 비밀번호 입니다.');
			$('#chkReg').css('color', 'blue');
			return true;
		}
	}

	function checkPW2() {
		if ($('#password').val() != $('#chk_password').val()) {
			$('#chkPw').text('입력한 비밀번호가 일치하지 않습니다');
			$('#chkPw').css('color', 'red');
			return false;
		} else {
			$('#chkPw').text('비밀번호가 일치합니다');
			$('#chkPw').css('color', 'blue');
			return true;
		}
	}

	function checkName() {
		var reg_name = /^[가-힣a-zA-Z]+$/;
		if (!reg_name.test($.trim($("[name=usersName]").val()))) {
			$('#chkName').text('이름은 한글 혹은 영문자로만 입력해주세요');
			$('#chkName').css('color', 'red');
			return false;
		} else {
			return true;
		}
	}

	function joinChk() {

		if (!checkEmail()) {
			alert("이메일을 확인해주세요");
			document.getElementById("email").focus();
			return false;
		}
		if (!checkPW()) {
			alert("비밀번호를 확인해주세요");
			document.getElementById("password").focus();
			return false;
		} else if (!checkPW2()) {
			alert("비밀번호22 확인해주세요");
			document.getElementById("chk_password").focus();
			return false;
		} else if (!checkName()) {
			document.getElementById("name").focus();
			return false;
		} else {
			return true;
		}
	}
</script>
<body>
	<div id="wrap">

		<div id="join">
			<div class="container">
				<h2 class="joinHead">SIGN IN</h2>
				<form action="/user/join" method="post" onsubmit="return joinChk();">
					<div class="email">
						<label for="email">이메일</label> <input type="email"
							name="usersEmail" id="email" value="" required>
						<!-- <button class="checking" id="checking">중복 확인</button> -->
						<input id="checking" type="button" class="checking" value="중복확인"
							required></input> <b class="hide"
							style="color: red; margin-top: -10px; margin-bottom: 10px; display: block;'"></b>
						<p id="checkid-msg" class="form-error">&nbsp;
					</div>
					<label for="password">비밀번호</label> <input type="password"
						name="usersPassword" id="password" required> <b
						id="chkReg"
						style="color: red; margin-top: -10px; margin-bottom: 10px; display: block;'"></b>
					<label for="password">비밀번호 확인</label> <input type="password"
						name="chk_password" id="chk_password" class="pw" required>
					<b id="chkPw"
						style="color: red; margin-top: -10px; margin-bottom: 10px; display: block;'"></b>

					<label for="name">이름</label> <input type="text" name="usersName"
						id="name" required>
					<b id="chkName"
						style="color: red; margin-top: -10px; margin-bottom: 10px; display: block;'"></b>
					<button type="submit">회원가입</button>
				</form>
			</div>
		</div>
		<!-- //login -->
	</div>
</body>
</html>