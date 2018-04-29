# Readme for Cheaters

This code was written by John Sigmon and Daniel Diamont for lab number 7 for Software Design and Implementation 2 at UT Austin. The code was last modified April 29, 2018.

---

## Purpose
<p>
    This program takes in a directory of files and checks for common substrings. It outputs a formatted list of the files with the highest number of similarities and how many similarities exist.
</p>

---

## Usage
<p>
Run the main class from the command line with:

```
java cheaters <filepath> <substring length> <similarity threshold>
```

</p>

<p>

`filepath` should be an absolute file path to the directory where the .txt documents are.
`substring lenth` is the length of the substrings for which you want to check.
`similarity threshold` is the number below which you want to ignore similarities.

</p>

---

## Classes

<p>
    The assignment contains two classes, Cheaters and Model.
</p>

### Cheaters

#### Variables

`file_list` is a List of the file names in the absolute file path to the directory where the desired .txt documents are.

`substring_len` is the desired length of substrings for which we want to check.

`threshold` is the number below which we want to ignore similarities.

#### Methods

`main` This is the program driver. It parses the files, runs our own measure of similarity software, and outputs the results.

`getFileList` returns a list of all the .txt files in the directory specified by `filepath` in the command line arguments.

`preProcess` goes through all of the specified .txt files and deletes punctuation while converting all A-Z characters to uppercase.

`parseFile` iterates through a .txt file and creates a hash for every possible combination of `substring_len` words. It stores these hashes in Model's `hashtable`, where the keys of the table are the hashes, and the values are the files which contain the same set of words (i.e., share the same hash).

### Model

<p>
</p>

#### Variables

`hashtable` is a Hashtable with integer keys, and values of linkedlists of integers. The keys represent the hashed code of a specific substring. The values are the indices associated with the file that the substring originated from.

`output_matrix` is an array of arrays of ints that contains the number of similarities between file i and file j, where i is a row and j is a column.

`output_dict` is a red-black tree that is instantiated with a sorted map built from flattening the output matrix.

#### Methods

`buildDictionary` flattens the matrix into a map and instantiates the red black tree with the map, which sorts it.

`printDictionary` prints the top n similarities, where n is passed in as an argument.

