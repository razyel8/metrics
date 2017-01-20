library(RUnit);

simpleHello = function() {
  return("Hello");
}

simpleOne = function() {
  return(1);
}

simpleThrowException = function(message) {
  stop(message);
}

testHello = function(){
  checkEquals("Hello", simpleHello());
}

testOne = function(){
  checkEqualsNumeric(1, simpleOne());
}

testException = function(){
  checkException(simpleThrowException("sample exception message"))
}

testHello()
testOne()
testException()
