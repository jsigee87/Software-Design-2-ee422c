# Readme for Cheaters

This code was written by John Sigmon and Daniel Diamont for lab number 7 for Software Design and Implementation 2 at UT Austin. The code was last modified April 28, 2018.

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

<p>
</p>

#### Methods

<p>
</p>

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

