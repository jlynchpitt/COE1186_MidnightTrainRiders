/*
* Filename: reportingMenu.java
* Author: Matt Snyder
* Last Edited: 10/12/2017
* File Description: The Reporting Menu
* 
* 
* 
* 
* 
* 
* 
* 
* 
* 
* 
* 
* THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HODLERS AND CONTRIBUTORS "AS IS" AND ANY
* EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
* OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCALIMED. IN NO EVENT
* SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, 
* INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING BUT NOT LIMITED
* TO, PROCUREMENT OF SUBSITUTE GOODS OR SERVICES; LOSS OF USE, DATA OR PROFITS; OR
* BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
* CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN
* ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF 
* SUCH DAMAGE.
*/

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


public class reportingMenu extends JFrame {
	private JFrame frame = new JFrame("Reporting");
	private JButton powerGraph = new JButton("Speed/Power Graph");
	private JButton passengerGraph = new JButton("Passenger Flow Graph");
	private JButton stationLog = new JButton("Station Log");
	private JButton delayLog = new JButton("Delay Log");
	private JButton incidentLog = new JButton("Incident Log");
	
	public reportingMenu() {
		render();
	}
	
	private void render() {
		JPanel panel = new JPanel(new GridLayout(5,1));
		panel.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
		panel.add(powerGraph);
		panel.add(passengerGraph);
		panel.add(stationLog);
		panel.add(delayLog);
		panel.add(incidentLog);
		frame.add(panel);
		
		frame.setSize(250, 500);
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
		
	}
}