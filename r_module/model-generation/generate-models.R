# install.packages("properties")
# install.packages('caTools');
# install.packages('randomForest');

propertiesFileDirectory <- "D:/Studies/MgrII/Projekt/wpmg/r_module/model-generation/app.properties";
trainingSetDirectory <- "D:/Studies/MgrII/Projekt/wpmg/r_module/model-generation/training-set.csv";
outputFileDirectory <- "D:/Studies/MgrII/Projekt/wpmg/r_module/model-generation/output.txt";

# propertiesFileDirectory <- "app.properties";
# trainingSetDirectory <- "training-set.csv";
# outputFileDirectory <- "/output.txt";

paste(getwd(), "app.properties")

#reade data from training set/properties files
trainingSet <- read.csv(trainingSetDirectory,  header=TRUE, sep=";");

library(properties);
myProperties <- read.properties(propertiesFileDirectory);


# : ---------------------------------- create formula ---------------------------------------------------

#read measurevar from properties file (our: is bug prone, or not)
measurevar <- myProperties$result;

#read names of metrcis used to train models and split them into
groupvars  <- c(strsplit(myProperties$test_metric_names, ","))[[1]];

form <- as.formula(paste(measurevar, paste(groupvars , collapse=" + "), sep=" ~ "))
# so formula is: "y1 ~ x1 + x2 + x3 + x4", y1 is result, x1 ... x4 are arguments

# : ---------------------------------- train multiple_linear_regr model ---------------------------------
model_multiple_linear_regr = lm(form, trainingSet);


# : ---------------------------------- train logistic_regr model ----------------------------------------
library(caTools);
model_logistic_regr <- glm (form, data = trainingSet, family = binomial);


# : ---------------------------------- train randomForest model -----------------------------------------
library(randomForest);
model_random_forest <- randomForest(x=trainingSet[,1:(ncol(trainingSet)-1)], y=factor(trainingSet[,"y1"]), 
                                    importance=TRUE, do.trace=1)

# : ---------------------------------- save result to output file  --------------------------------------

con <- file(outputFileDirectory)
sink(con, append=TRUE)
sink(con, append=TRUE, type="message")

model_multiple_linear_regr;
model_logistic_regr;
model_random_forest;
varImpPlot(model_random_forest)
