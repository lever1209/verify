package core.manual.pages;

import core.Global;

public class Basics implements Page {

	@Override
	public String[] getName() {
		return new String[] { "Basics", "syntax", "start", "beginning", "commands", "args", "arguments", "|", "&", "<",
				">", "]", "[" };
	}

	@Override
	public int getID() {
		return 0;
	}

	@Override
	public Object getContents() {
		return "Within the usage section of help on any command, you may have noticed these: [] <> | &"
				+ "\nthose are symbols many people use to show the user what to do"
				+ "\nhere is a cheatsheet in case you dont know or have forgotten"
				+ "\n[] Square Brackets mean all that is contained within are optional"
				+ "\n<> triangle brackets mean teh contents are required" + "\n| the pipe symbol means or"
				+ "\n& the ampersand means and"
				+ "\nUsing these, i will create some example commands that do not exist, to show you how to understand them"
				+ "\n" + Global.Prefix
				+ "example1 <argument 1> [argument 2] - this means the command named example1 requires you to give it"
				+ "\nan argument in spot 1, but the second is optional, meaning the command will run like this `"
				+ Global.Prefix + "example1 abcd` and like this `" + Global.Prefix
				+ "example1 abcd efgh` but not like this `" + Global.Prefix + "example1`"
				+ "\nThis next example is using the pipe, `" + Global.Prefix
				+ "example2 <-d | -o>` this means the command requires either -d or -o to be used"
				+ "\n\nive been typing for 3 hours now, so im going to leave the file like this, "
				+ "if you want more information, yell at me in the support server and ill finish this page";
	}

}
