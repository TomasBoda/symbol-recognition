# Handwritten Symbol Recognition Using Matrix Distances
by [Tomáš Boďa](https://github.com/TomasBoda)

## About
The aim of this project was to create a simple GUI tool which is able to distinguish between different **handwritten symbols** based on the probability of coincidence, which is calculated as a **matrix distance** between the trained symbols and the symbol that is being recognized.

## Technical details
The project is written entirely in Java, with no external packages or libraries being used.

## Graphical User Interface (GUI)
The GUI for this application is built upon the **Java Spring** framework (JFrame, JPanel, JButton, JTextField).
<br>
<br>
The upper part of the window is a square canvas used for drawing and displaying handwritten symbols. The bottom part of the window serves as a toolbar with a mode switch, an action button and an input field used for assigning keys for the handwritten symbols and for outputing the estimations of the handwritten symbols.

<img width="1440" alt="Screenshot 2022-09-04 at 14 46 26" src="https://user-images.githubusercontent.com/58137476/188314203-2e44519a-67e3-457c-a338-45e1f8e99b26.png">


## Application Lifecycle
The application has three modes, in which it operates.

### Train mode
In this mode, the user is able to draw handwritten symbols onto the canvas. After finishing drawing, the user enters a label for the symbol into the text field in the bottom part of the application. The label is a textual representation of the handwritten symbol (for example a digit or a letter, even a whole word). Then, the action button labeled "Train" saves the (label, symbol) pair into the training dataset later used for symbol recognition. After pressing the "Train" button, both the canvas and the text field are cleared and ready for the next input.

### Estimate mode
In this mode, the user is, again, able to draw handwritten symbols onto the canvas. The input field is, however, disabled. After drawing a symbol, the user can press the "Estimate" button, after which the application tries to recognize the symbol and finally outputs the estimated result into the text field. After the user clicks the "Estimate" button, the canvas gets cleared and is ready for the next input.

### Show mode
In this mode, the user can take a look at the trained dataset. The user can enter a label into the text field, press the "Show" button and if the label exists in the dataset, the resulting matrix representation of the averaged symbol data gets printed onto the canvas. If the label does not exist, a popup window with an error message is shown to the user.

The action button can be alternatively activated (pressed) by pressing the ENTER key when the input field is focused.
<br>

## Idea behind the algorithm
Each handwritten symbol is represented as a 2-dimensional array (an instance of the `Matrix` class) of pixels in the canvas, more specifically, as RGB values of these pixels.
<br>
The same way we are able to calculate the distances between vectors, we can also calculate **distances between matrices**. This particular thing comes in handy in handwritten symbol recognition. Since we can compare two symbols using matrix distances, we have some sort of metrics on how similar these two symbols are. Therefore, if we store different symbols under different labels, we can then compare our new handwritten symbol with all other trained symbols and see which of them is the closest, or the most similar to our symbol.
<br>
<br>
The math formula for calculating the **distance between two matrices** is as follows:
<br>
<br>
$||A - B|| = \sqrt{\sum_{i=1} \sum_{j=1} (a_{ij}-b_{ij})^2}$
<br>
<br>
The $a_{ij}$ and the $b_{ij}$ values are the specific pixels of the two matrices we are comparing. However, with RGB pixel values, we need some sort of metrics to compare these two values and tell how similar they are. Luckily, since we are only recognizing symbols, we are working only with **grayscale images** - canvas is black, handwritten symbols are white and the averaged data could be anywhere between 0-255 RGB values. Therefore, we only need to compare one of the red, green, blue values, since each pixel has these three values the same. So, the distance between two pixels is then calculated as `(pixel1.red - pixel2.red)^2`.

## Data Structures
For storing the trained symbols, I opted for a simple **hashtable**, which feels like a natural and an ideal option for this task. The **keys** are the symbol **labels** and the **values** are `ArrayList` instances, where all the symbols under the given label are stored.
<br>
After drawing a symbol and assigning a label to it, we check whether the label (key) is already present in the hashtable and if so, we add the new symbol to the end of the `ArrayList`. Otherwise, we create a new entry in the hashtable and store the symbol under the new key.
<br>
<br>
After switching to the **Estimate Mode**, all the trained data is processed, recalculated and stored into a new hashtable with averaged symbol values. For each key in the new hashtable, a new symbol matrix is generated, with each pixel calculated as the sum of the trained symbol pixels divided by the number of the trained symbols for the given key. In this way, we create some sort of an **averaged symbol matrix** with the pixel values ranging from 0-255.
<br>
This new hashtable is the used for the final symbol recognition, since it contains all the trained data merged into separate averaged matrices used for calculating the distance and estimating the closest symbols. These averaged matrices can also be viewed in the **Show Mode** by typing the symbol label and displaying it.

## What could be improved
At this point, after we open the application, the dataset is empty and first we need to train the algorithm by feeding new symbols into the training dataset. That is often tedious, mostly when testing the application or just playing with the values. Therefore, a new system could be implemented which saves the training dataset either as `.png` files or textual representation of the RGB values. On the application start, the saved data could be initialized and loaded into the training dataset, so the application would be ready for use.

## Result
The resulting application is quite successful in recognizing various symbols. However, the algorithm is very simple in a way that it cannot distinguish between different curves and shapes. It only checks the number of pixel coincidences and color distances, which suffices with simple symbols such as digits, however, is not optimal and precise with more complex shapes. But all in all, the results are quite impressive and most of the time classify the symbols correctly.


**Handwritten Symbol Recognition Using Matrix Distances** by [Tomáš Boďa](https://github.com/TomasBoda)
