# Handwritten Symbol Recognition Using Matrix Distances
by [Tomáš Boďa](https://github.com/TomasBoda)

## About
The aim of this project was to create a simple GUI tool which is able to distinguish between different handwritten symbols based on the probability of coincidence, which is calculated as a matrix distance between the trained symbols and the symbol that is being recognized.

## Technical details
The project is written entirely in Java, with no external packages or libraries being used.

## Graphical User Interface (GUI)
The GUI for this application is built upon the Java Spring framework (JFrame, JPanel, JButton, JTextField).
<br>
<br>
The upper part of the window is a square canvas used for drawing and displaying handwritten symbols. The bottom of the window serves as a toolbar with a mode switch, an action button and an input field used for assigning keys for the handwritten symbols and for outputing the estimations of the handwritten symbols.

## Application Life Cycle
The application has three modes, in which it operates.

### Train mode
In this mode, the user is able to draw handwritten symbols onto the canvas. After finishing drawing, the user enters a label for the symbol into the text field in the bottom part of the application. The label is a textual representation of the handwritten symbol (for example a digit or a letter, even a whole word). Then, the action button labeled "Train" saves the (label, symbol) pair into the training dataset later used for symbol recognition. After pressing "Train" button, both the canvas and the text field are cleared and ready for the next input.

### Estimate mode
In this mode, the user is, again, able to draw handwritten symbols onto the canvas. The input field is, however, disabled. After drawing a symbol, the user can press the "Estimate" button, after which the application tries to recognize the symbol and finally outputs the estimated result into the text field. After the user clicks the "Estimate" button, the canvas gets cleared and is ready for the next input.

### Show mode
In this mode, the user can take a look at the trained dataset. The user can enter a label into the text field, press the "Show" button and if the label exists in the dataset, the resulting matrix representation of the averaged symbol data gets printed onto the canvas. If the label does not exist, a popup window with an error message is shown to the user.

### Idea behind the algorithm
Each handwritten symbol is represented as an array of pixels in the canvas, more specifically, as RGB values of each pixel. Therefore, these RGB values can be stored as matrices.
<br>
The same way we are able to calculate the distances between vectors, we can also calculate the distances between matrices. This particular thing comes in handy in handwritten symbol recognition. Since we can compare two symbols using matrix distances, we have some sort of metrics on how similar two symbols are. Therefore, if we store different symbols under different labels, we can then compare our new handwritten symbol with all other trained symbols and see which of them is the closest, or more similar to our symbol.
<br>
<br>
The math formula for calculating the distance between two matrices is as follows:
<br>
<br>
$||A - B|| = \sqrt{\sum_{i=1} \sum_{j=1} (a_{ij}-b_{ij})^2}$

**Handwritten Symbol Recognition Using Matrix Distances** by [Tomáš Boďa](https://github.com/TomasBoda)
