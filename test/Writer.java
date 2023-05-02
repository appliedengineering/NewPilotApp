
import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import javax.swing.KeyStroke;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author jeffrey
 */
public class Writer {
    
    static String s = "Programming captivates many individuals, and it is not difficult to see why. It is a field that allows individuals to create something from nothing, utilizing their creativity and technical knowledge to solve complex problems. Programming is a field that requires constant learning, as new technologies and programming languages are developed, which makes it an exciting and dynamic field to be in. The satisfaction that comes from building something that works, as well as the thrill of seeing your code come to life, are among the many reasons why programming captivates many people.\n" +
"\n" +
"Programming is a field that can be both challenging and rewarding, and this is why it captivates many individuals. It is a field that requires a logical mindset, as well as an ability to think critically and problem-solve. As a programmer, you are often presented with complex problems that require a lot of thought and effort to solve. However, the reward for solving these problems can be significant, as you get to see the fruits of your labor in the form of a functioning program or application.\n" +
"\n" +
"The creativity that programming allows is another reason why it captivates many people. While programming is often associated with logic and problem-solving, there is also a creative aspect to it. Programmers have to think outside the box to come up with unique solutions to complex problems. This creativity is not just limited to problem-solving, as programmers can also use their creativity to design visually appealing user interfaces or create engaging game mechanics.\n" +
"The fact that programming is such a versatile field is another reason why it captivates many people. Programming can be used in almost any industry, from finance to healthcare to entertainment. This means that as a programmer, you have the opportunity to work on a wide variety of projects and gain experience in different industries. This versatility also means that you can choose to specialize in a specific area of programming if you so choose.\n" +
"\n" +
"Finally, programming is a field that is constantly evolving, and this is yet another reason why it captivates many people. New programming languages are being developed all the time, and existing languages are constantly being updated and improved. This means that as a programmer, you must constantly learn and adapt to new technologies and programming paradigms. This continuous learning and growth keep the field exciting and dynamic, and it is one of the many reasons why programming captivates so many individuals.\n" +
"\n" +
"\n";
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    public static void main(String[] args) throws Exception {
        
        Thread.sleep(1000);
        int i = 0;
        
        try {
            Robot robot = new Robot();
            while (i<s.length()) {
                
            KeyStroke keyStroke = KeyStroke.getKeyStroke(s.substring(i, i+1));
            // if(keyStroke!= null) {
//            if(Character.isUpperCase(s.substring(i, i+1).toCharArray()[0])) {
//                robot.keyPress(KeyEvent.VK_SHIFT);
//                robot.keyPress(keyStroke.getKeyCode());
//                robot.keyRelease(KeyEvent.VK_SHIFT);
//
//            } else {
if(keyStroke != null)
                robot.keyPress(keyStroke.getKeyCode());
 if(keyStroke != null)                               robot.keyRelease(keyStroke.getKeyCode());
            i++;

}
            // }
            
        } catch (AWTException e) {
            e.printStackTrace();
        }
        
    }
    
}
