<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<html>
	<head>
	 	<title>게시판</title>
	</head>
	<body>
	
		<div id="root">
			<header>
				<h1> 게시판</h1>
			</header>
			<hr />
			 
			<nav>
			  홈 - 글 작성
			</nav>
			<hr />
			
			<section id="container">
				<form role="form" method="post" action="write">
					<table>
						<tbody>
							<tr>
								<td>
									<label for="harbor">항만</label><input type="text" id="HARBOR" name="HARBOR" />
								</td>
							</tr>	
							<tr>
								<td>
									<label for="dock">부두</label><input type="text" id="DOCK" name="DOCK" />
								</td>
							</tr>
							<tr>
								<td>
									<label for="company">회사</label><input type="text" id="COMPANY" name="COMPANY" />
								</td>
							<tr>
							<tr>
								<td>
									<label for="password">비밀번호</label><input type="text" id="password" name="password" />
								</td>
							</tr>
								<td>						
									<button type="submit">작성</button>
								</td>
							</tr>			
						</tbody>			
					</table>
				</form>
			</section>
			<hr />
		</div>
	</body>
</html>