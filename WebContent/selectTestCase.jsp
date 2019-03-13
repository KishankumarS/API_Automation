<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"[]>
<%@ taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@ taglib prefix="h" uri="http://java.sun.com/jsf/html"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html xmlns="http://www.w3.org/1999/xhtml" dir="ltr" lang="en-US"
	xml:lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>Home</title>
<link rel="stylesheet" href="style.css" type="text/css" media="screen" />
<script type="text/javascript" src="jquery.js"></script>
<script type="text/javascript" src="script.js"></script>

</head>

<body>
	<script>
		function toggle(source) {
			checkboxes = document.getElementsByName('testCase');
			for (var i = 0, n = checkboxes.length; i < n; i++) {
				checkboxes[i].checked = source.checked;
			}
		}
	</script>
	<form action="/RegressionBase/hello">
		<div id="art-main">
			<div class="cleared reset-box"></div>
			<div class="art-box art-sheet">
				<div class="art-box-body art-sheet-body">
					<div class="art-header">
						<div class="art-bar art-nav"></div>
						<div class="art-logo">
							<table>
								<tr>
									<td>
										<div class="art-logo-name">
											<img style="top: 95px;" src="resources/Images/swoosh1.png">
										</div>
									</td>
									<td>
										<h3 class="art-logo-name" align="right">Sprint Automation</h3>
									</td>
								</tr>
							</table>
						</div>
					</div>
				</div>
				<div class="cleared reset-box"></div>
				<div class="art-layout-wrapper">
					<div class="art-content-layout">
						<div class="art-content-layout-row">
							<div class="art-layout-cell art-content">
								<div class="art-box art-post">
									<div class="art-box-body art-post-body">
										<div class="art-post-inner art-article">
											<table align="center">
												<tr>
													<td>
														<div class="tabbable">
															<ul class="tabs">
																<li><a href="#pig">Regression</a></li>
																<li><a href="#cat">Smoke</a></li>
															</ul>
															<div class="tabcontent">
																<div id="pig" class="tab">
																	<table align="left">
																		<tr>
																			<td><div class="art-postcontent">
																					<h3>Select the test cases:</h3>
																				</div></td>
																		</tr>

																		<tr>
																			<td><input type="checkbox"
																				onClick="toggle(this)" /><b>Select All</b></td>
																		</tr>

																		<tr>
																			<td><c:forEach var="j"
																					items="${readTestCasesBean.names0}">


																					<tr>
																						<td><input type="checkbox" name="testCase"
																							value="${j}"> <c:out value="${j}" /></td>
																					</tr>
																				</c:forEach></td>
																		</tr>



																	</table>
																</div>
																<div id="cat" class="tab">
																	<table align="left">


																		<tr>
																			<td><div class="art-postcontent">
																					<h3>Select the test cases:</h3>
																				</div></td>
																		</tr>

																		<tr>
																			<td><input type="checkbox"
																				onClick="toggle(this)" /><b>Select All</b></td>
																		</tr>

																		<tr>
																			<td><c:forEach var="j"
																					items="${readTestCasesBean.names1}">


																					<tr>
																						<td><input type="checkbox" name="testCase"
																							value="${j}"> <c:out value="${j}" /></td>
																					</tr>
																				</c:forEach></td>
																		</tr>

																	</table>

																</div>
															</div>
														</div>
													</td>
													<td>
														<table align="right">
															<tr>
																<td><div class="art-postcontent">
																		<h3>Select Environment :</h3>
																	</div></td>
															</tr>

															<tr>


																<td>Environment: <select name="environment">
																		<c:forEach var="k" items="${readEnvNamesBean.names}">
																			<option value="${k}"><c:out value="${k}" /></option>
																		</c:forEach>
																</select></td>

															</tr>

														</table>
													</td>

													<tr>
														<td>
															<div class="art-box art-post">
																<div class="art-box-body art-post-body">
																	<input type="submit" class="art-button"
																		value="Run TestCases"></input>
																</div>
															</div>
														</td>
													</tr>
											</table>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</form>

</body>

</html>
