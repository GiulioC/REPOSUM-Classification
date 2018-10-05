from sklearn.metrics import recall_score, precision_score,f1_score
from sklearn.ensemble import RandomForestClassifier
from sklearn.model_selection import KFold
from sklearn.externals import joblib
import matplotlib.pyplot as plt
import text_processing as tp
from random import randint
import pandas as pd
import numpy as np
import sys, math

#train options
cross_validation = False
#train data options
min_df = 0.0
max_df = 0.8
n_features = 60000

def feed_data():
    for index,row in phil_text.iterrows():
        yield row['title']
    for index,row in nphil_text.iterrows():
        yield row['title']

def feed_data_titles(df):
    for index,row in df.iterrows():
        yield row['title']

def feed_classifier():
    yield TDmatrix1
    yield TDmatrix2

cv = tp.build_vectorizer("cv", min_df, max_df, n_features)
analyzer = cv.build_analyzer()
vectorizer = tp.build_vectorizer("tfidf")

# read and preprocess text data
positive_samples_train = pd.read_csv("data/new_philosophy_train.csv")
positive_samples_train = pd.read_csv("data/nophilosophy_train.csv")
test_samples = pd.read_csv("data/test_set_1000.tsv", delimiter="\t", names=['title','creator','university','publisher', 'year','abstract','type','subject','id','philosophy'])

print("Positive samples_train:",positive_samples_train.count()[0])
print("Negative samples_train:",negative_samples_train.count()[0])
print("Test samples:",test_samples.count()[0])

phil_titles = pd.DataFrame(tp.lemmatize_data(positive_samples_train[['title']],"title"), columns=['title'])
phil_titles = tp.preprocess_dataframe(phil_titles, analyzer)
nphil_titles = pd.DataFrame(tp.lemmatize_data(negative_samples_train[['title']],"title"), columns=['title'])
nphil_titles = tp.preprocess_dataframe(nphil_titles, analyzer)
phil_text = phil_titles
nphil_text = nphil_titles

test_titles = pd.DataFrame(tp.lemmatize_data(test_samples[['title']],"title"), columns=['title'])
test_titles = tp.preprocess_dataframe(test_titles, analyzer)
test_text = test_titles

# transform text data into vector space
vectorizer.fit(feed_data())
joblib.dump(vectorizer, "models/vectorizer.pkl")
TDmatrix1 = vectorizer.transform(feed_data_titles(phil_text))
TDmatrix2 = vectorizer.transform(feed_data_titles(nphil_text))
TDmatrix_test = vectorizer.transform(feed_data_titles(test_text))
print("Vocabulary:",len(vectorizer.vocabulary_))
print("TDmatrix 1:",TDmatrix1.shape)
print("TDmatrix 2:",TDmatrix2.shape)
print("TDmatrix test:",TDmatrix_test.shape)

train_data = []
test_data = []
labels = []
labels_test = []
for i in range(TDmatrix1.shape[0]):
    train_data.append(TDmatrix1[i].A[0])
    labels.append(1)
for i in range(TDmatrix2.shape[0]):
    train_data.append(TDmatrix2[i].A[0])
    labels.append(0)
for i in range(TDmatrix_test.shape[0]):
    test_data.append(TDmatrix_test[i].A[0])
    labels_test.append(test_samples.iloc[i]['philosophy'])

#shuffle data and labels
s = np.arange(len(train_data))
np.random.shuffle(s)
train_data = np.array(train_data)[s]
labels = np.array(labels)[s]

# classifier
clf = RandomForestClassifier(max_depth=None,
                                random_state=None,
                                n_estimators=50,
                                max_features=0.6,
                                n_jobs=-1, #WARNING: consider changing it
                                verbose=2)

if cross_validation:
    #10-fold cross-validation
    k_fold = KFold(n_splits=10)
    it = 1
    accuracies = []
    precisions = []
    recalls = []
    f1scores = []
    for train, test in k_fold.split(train_data):
        y_true = labels[test]
        print("cv iteration",it,"...")
        clf.fit(train_data[train], labels[train])
        y_pred = clf.predict(train_data[test])
        acc = np.mean(np.equal(y_pred,labels[test]))
        accuracies.append(acc)
        precisions.append(precision_score(y_true, y_pred))
        recalls.append(recall_score(y_true, y_pred))
        f1scores.append(f1_score(y_true, y_pred))

        print("accuracy at iteration",it,":",acc)
        print("precision at iteration",it,":",precision_score(y_true, y_pred))
        print("recall at iteration",it,":",recall_score(y_true, y_pred))
        print("f1 at iteration",it,":",f1_score(y_true, y_pred))
        it += 1
    print("\n\ncross-validation accuracy:",np.mean(np.array(accuracies)))
    print("cross-validation precision:",np.mean(np.array(precisions)))
    print("cross-validation recall:",np.mean(np.array(recalls)))
    print("cross-validation f1:",np.mean(np.array(f1scores)))
print("\n\n")

y_true = np.array(test_samples['philosophy'].tolist(), dtype=np.int64)
clf.fit(train_data, labels)
y_pred = clf.predict(test_data)
acc = np.mean(np.equal(y_true, y_pred))
precision = precision_score(y_true, y_pred)
recall = recall_score(y_true, y_pred)
f1score = f1_score(y_true, y_pred)
print("\n\ntest set Accuracy:",acc)
print("test set Precision:",precision)
print("test set Recall:",recall)
print("test set F1:",f1score)

joblib.dump(clf, "models/randomforestCLF.pkl")
