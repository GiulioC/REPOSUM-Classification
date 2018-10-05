from sklearn.externals import joblib
import text_processing as tp
import dataset_utils as dsu
import pandas as pd

vectorizer = joblib.load("models/vectorizer_noAbs.pkl")
analyzer = vectorizer.build_analyzer()

data = dsu.read_dataset_UK_ethos(True)
print("data with abs size:",data.count())
data_titles = pd.DataFrame(tp.lemmatize_data(data[['titolo']],"titolo"), columns=['titolo'])
data_titles = tp.preprocess_dataframe(data_titles, analyzer)
data_text = data_titles

preprocessed_data = []
for index,row in data.iterrows():
    preprocessed_data.append(row['titolo'])
print("preprocessed data size:",len(preprocessed_data))

data.loc[:,"preprocessed_data"] = preprocessed_data

data.to_csv("preprocessed_data/ethos_abs_preprocessed.csv",index=None, columns=["id","titolo","autore","univ","publisher","anno","abs","tipo","argomento","preprocessed_data"])


data = dsu.read_dataset_UK_ethos(False)
print("data with no abs size:",data.count())
data_titles = pd.DataFrame(tp.lemmatize_data(data[['titolo']],"titolo"), columns=['titolo'])
data_titles = tp.preprocess_dataframe(data_titles, analyzer)
data_text = data_titles

preprocessed_data = []
for index,row in data_text.iterrows():
    preprocessed_data.append(row['titolo'])
print("preprocessed data size:",len(preprocessed_data))

data.loc[:,"preprocessed_data"] = preprocessed_data

data.to_csv("preprocessed_data/ethos_no_abs_preprocessed.csv",index=None, columns=["id","titolo","autore","univ","publisher","anno","tipo","argomento","preprocessed_data"])
