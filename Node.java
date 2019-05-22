public class Node {
        char letterV;  // letter of that cell from String v
        char letterW;  // letter of that cell from String w
        double score;  // score of that position in the table
        Node left;     // spot the the left of the current cell
        Node top;      // spot above of the current cell
        Node topLeft;  // spot to the top-left of current cell
        Node previous; // spot where the score came from
        
        
        public Node[][] createNodeTable(Node[][] table, String v, String w, double match, double mismatch, double gap) {
            // cell at (0, 0)
            Node zero = new Node();
            zero.score = 0;
            zero.left = null;
            zero.top = null;
            zero.topLeft = null;
            zero.previous = null;
            table[0][0] = zero;
            
            // makes first row of table
            for (int i = 1; i <= v.length(); i++) {
              Node cell = new Node();
              cell.left = table[i-1][0];
              cell.previous = table[i-1][0];
              cell.top = null;
              cell.topLeft = null;
              cell.score = i*gap;
              cell.letterV = v.charAt(i-1);
              cell.letterW = '-';
              table[i][0] = cell;
            }

            // makes first column of table
            for (int j = 1; j <= w.length(); j++) {
              Node cell = new Node();
              cell.top = table[0][j-1];
              cell.previous = table[0][j-1];
              cell.left = null;
              cell.topLeft = null;
              cell.score = j*gap;
              cell.letterW = w.charAt(j-1);
              cell.letterV = '-';
              table[0][j] = cell;
            }

            // makes rest of the table
            for (int i = 1; i <= v.length(); i++) {
              for (int j = 1; j <= w.length(); j++) {
                  Node cell = new Node();
                  cell.left = table[i-1][j];
                  cell.top = table[i][j-1];
                  cell.topLeft = table[i-1][j-1];
                  double diagScore = cell.topLeft.score + 
                      (v.charAt(i-1) == w.charAt(j-1) ? match : mismatch);
                  double leftScore = cell.left.score + gap;
                  double topScore = cell.top.score + gap;
                  double bestScore = Math.max(Math.max(topScore, leftScore), diagScore);
                  cell.score = bestScore;

                  if (bestScore == diagScore)
                      cell.previous = cell.topLeft;
                  else if (bestScore == topScore)
                      cell.previous = cell.top;
                  else
                      cell.previous = cell.left;

                  if (cell.previous == cell.topLeft) {
                      cell.letterV = v.charAt(i-1);
                      cell.letterW = w.charAt(j-1);
                  }
                  else if (cell.previous == cell.top) {
                      cell.letterV = '-';
                      cell.letterW = w.charAt(j-1);
                  }
                  else {
                      cell.letterV = v.charAt(i-1);
                      cell.letterW = '-';
                  }

                  table[i][j] = cell;
              }
           }
           return table;
       }
    }
