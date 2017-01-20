message1 <- "Starting . Issue: SPR-14527"
message2 <- "Starting point for reactive WebSocket support Includes basic abstractions and an RxNetty support to start. Issue: SPR-14527"
messageFail1 <- "Polishing"
messageFail2 <- "Add PATCH method operation to RestTemplate This commit adds a HTTP PATCH operation to the RestTemplate: patchForObject. As with most operations, there are three variants: varargs, Map, and URI based"

getIssueNumber <- function(message){
  if(!is.null(grep("Issue: ", message, value = TRUE))) {
    splitted <- strsplit(message, "Issue: ")
    splitted <- strsplit(splitted[[1]][2], "\\n")
    return(splitted[[1]][1])
    
  }
  return(null)
}

#for (i in 1:1000) {
#  print(getIssueNumber(toString(gitData$V4[i])))
#}


#Issue number with SPR like "SPR-14527"
findIssueByNumber <- function(number){
  result <- which(grepl(number, jiraData$V1))
  return(jiraData[result,])
}

dataTable <- data.frame("", "", "", "", "", "","", "", "")
names(dataTable) <- names(jiraData)
issuesCount <- 1

connectIssuesAndCommits <- function(){
  issuesCount <- 1
   for (i in 1:10000) {
    issueNumber <- getIssueNumber(toString(gitData$V4[i]))
    issue <- findIssueByNumber(toString(issueNumber))
    if(nrow(issue)>0){
      rbind(dataTable, c(issue[[1]][1], issue[[1]][2], issue[[1]][3], issue[[1]][4], issue[[1]][5], issue[[1]][5], issue[[1]][6], issue[[1]][7]),  issue[[1]][8], issue[[1]][9])
      issuesCount <- issuesCount+1
      print(issuesCount)
    }
   }
  print(dataTable)
    #gd <- print(gitData[20,])
    
    # dataTable <- c(issue, gitData[20,])
    # print(dataTable)
  
  # for (i in 1:1000) {
  #   issue <- getIssueNumber(toString(gitData$V4[i]))
  #   dataTable <- c(dataTable, issue + gitData[2,])
  # }
}
connectIssuesAndCommits()

