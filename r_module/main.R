# dat = read.csv("data2.csv", header = TRUE)
# 
# myVar <- 2+2
# myVar2 <- dat[1]

## Not run:
# require(xlsx)
# # example of reading xlsx sheets
# jiraData <- read.xlsx("data2.xlsx", 1, header = FALSE) # read the second sheet
#gitData <- read.xlsx("gitData.xlsx", 1, header = FALSE) # read the second sheet
# # example of writing xlsx sheets
# file <- paste(tempfile(), "xlsx", sep=".")
# write.xlsx(USArrests, file=file)
# ## End(Not run)
gitData = read.csv2("dataGit2.csv", header = FALSE)
gitData$V6 = as.Date(gitData$V6, "%Y-%m-%d")


jiraData = read.csv2("dataJira.csv", header = FALSE)
jiraData$V2 = as.Date(jiraData$V2, "%Y-%m-%d")
jiraData$V3 = as.Date(jiraData$V3, "%Y-%m-%d")