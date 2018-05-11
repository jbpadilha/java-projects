package com.joaopadilha;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class DrawerFile {

	
	public static void main(String[] args) {
		
		String path = "input.txt";
		
		List<String> listLines = new ArrayList<String>();
		try {
			InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(path);
			OutputStream output = new FileOutputStream("output.txt");
			listLines = DrawerFile.readIntoLines(inputStream);
			
			//Canvas C w h Create Canvas: Should create a new canvas of width w and height h.
		    Optional<String> lineCanvas = listLines.stream().filter(s->s.contains("C")).findFirst();
		    if(lineCanvas.isPresent()) {
		    	createCanvas(lineCanvas.get(), output);
		    }
		    
		    //L x1 y1 x2 y2 Create Line: Should create a new line from (x1,y1) to (x2,y2). Currently
		    // only horizontal or vertical lines are supported. Horizontal and vertical
		    // lines will be drawn using the 'x' character.
		    List<String> newLine = listLines.stream().filter(s->s.contains("L")).collect(Collectors.toList());
		    if(newLine !=null && newLine.size()>0) {
		    	createCanvasWithLine(lineCanvas.get(), newLine, output);
		    }
		    
		    //Create Rectangle: Should create a new rectangle, whose upper left
		    //corner is (x1,y1) and lower right corner is (x2,y2). Horizontal and
		    //vertical lines will be drawn using the 'x' character.
		    List<String> lineRetagle = listLines.stream().filter(s->s.contains("R")).collect(Collectors.toList());
		    if(lineRetagle !=null && lineRetagle.size()>0) {
		    	createCanvasRetangle(lineCanvas.get(), lineRetagle, output);
		    }
		    
		    //Bucket Fill: Should fill the entire area connected to (x,y) with "colour"
		    //c. The behaviour of this is the same as that of the "bucket fill" tool in
		    //paint programs.
		    List<String> lineBucket = listLines.stream().filter(s->s.contains("B")).collect(Collectors.toList());
		    if(lineBucket !=null && lineBucket.size()>0) {
		    	createCanvasBucket(lineCanvas.get(), lineBucket, output);
		    }
		    
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	    
	}
	
	private static void createCanvasBucket(String string, List<String> lineBucket, OutputStream output) {
		
		
	}

	private static void createCanvasRetangle(String string, List<String> lineRetagle, OutputStream outputStream) {
		
		
	}

	private static void createCanvasWithLine(String lineCanvas, List<String> newLine, OutputStream outputStream) {
		String[] lines = lineCanvas.split(" ");
		Integer width = 0;
		Integer height = 0;
		Integer columnBegin;
		Integer lineColumnBegin;
		Integer columnFinal;
		Integer lineColumnFinal;
		List<String> canvas = new ArrayList<String>();
		
		if (lines.length>2) {
			width = Integer.parseInt(lines[1]);
			height = Integer.parseInt(lines[2]);
			
			//Line 1
			StringBuilder charLine1 = new StringBuilder();
			for(int i=0;i<width;i++) {
				charLine1.append("-");
			}
			canvas.add(charLine1.toString());
			
			//Line 2 to height-1 - Create Line Inside
			for(String newLineX : newLine) {
				String[] lineX = newLineX.split(" ");
				if (lineX.length>2) {
					columnBegin =  Integer.parseInt(lineX[1]);
					lineColumnBegin =  Integer.parseInt(lineX[2]);
					columnFinal =  Integer.parseInt(lineX[3]);
					lineColumnFinal =  Integer.parseInt(lineX[4]);
					
					for(int i=1;i<=height;i++) {
						StringBuilder charLinesBody = new StringBuilder();
						charLinesBody.append("|");
						
						if(i >= lineColumnBegin && i <= lineColumnFinal) {
							for(int j=1;i<width-1;j++) {
								if(j >= columnBegin && j <= columnFinal) {
									charLinesBody.append("x");
								}
								else {
									charLinesBody.append(" ");
								}
							}
						}
						else {
							for(int j=1;i<width-1;j++) {
								charLinesBody.append(" ");
							}
						}
						charLinesBody.append("|");
						canvas.add(charLinesBody.toString());
					}
					
				}
			}
			
			
			//Line Bottom
			StringBuilder charLineBottom = new StringBuilder();
			for(int i=0;i<width;i++) {
				charLineBottom.append("-");
			}
			canvas.add(charLineBottom.toString());
			
			writeLines(canvas, outputStream);
		}
		
	}

	private static void createCanvas(String lineCanvas, OutputStream outputStream) {
		String[] lines = lineCanvas.split(" ");
		Integer width = 0;
		Integer height = 0;
		List<String> canvas = new ArrayList<String>();
		if (lines.length>2) {
			width = Integer.parseInt(lines[1]);
			height = Integer.parseInt(lines[2]);
			
			//Line 1
			StringBuilder charLine1 = new StringBuilder();
			for(int i=0;i<width;i++) {
				charLine1.append("-");
			}
			canvas.add(charLine1.toString());
			
			//Line 2 to height-1
			for(int i=1;i<=height;i++) {
				StringBuilder charLinesBody = new StringBuilder();
				charLinesBody.append("|");
				for(int j=1;i<width-1;j++) {
					charLine1.append(" ");
				}
				charLinesBody.append("|");
				canvas.add(charLinesBody.toString());
			}
			
			//Line Bottom
			StringBuilder charLineBottom = new StringBuilder();
			for(int i=0;i<width;i++) {
				charLineBottom.append("-");
			}
			canvas.add(charLineBottom.toString());
			
			writeLines(canvas, outputStream);
		}
	}
	
	public static List<String> readIntoLines(InputStream inputStream) {
	    List<String> ret = new ArrayList<String>();
	    try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
	      String line = reader.readLine();
	      while (line != null) {
	        ret.add(line);
	        line = reader.readLine();
	      }
	    } catch (IOException e) {
	      String message = "Error occurred while reading the InputStream: " + e.getLocalizedMessage();
	      e.printStackTrace();
	      throw new RuntimeException(message, e);
	    }
	    return ret;
	  }

	public static void writeLines(List<String> lines, OutputStream outputStream) {
	    try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream))) {
	      int lineNumber = 0;
	      for (String line : lines) {
	        if (lineNumber++ > 0) {
	          writer.newLine();
	        }
	        writer.write(line);
	      }
	    } catch (IOException e) {
	      String message = "Error occurred while wrting the OutputStream: " + e.getLocalizedMessage();
	      e.printStackTrace();
	      throw new RuntimeException(message, e);
	    }
	  }

	
	
}
