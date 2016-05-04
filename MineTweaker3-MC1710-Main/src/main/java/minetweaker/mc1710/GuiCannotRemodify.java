package minetweaker.mc1710;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;

/**
 * This screen is shown when a client has been modified with a permanent
 * modification and the player logs into a game with a different script. It asks
 * the player to restart the game.
 * 
 * @author Stan Hebben
 */
public class GuiCannotRemodify extends GuiScreen {
	private final String message1;
	private final String message2;
	private final String message3;

	public GuiCannotRemodify(String message1, String message2, String message3) {
		this.message1 = message1;
		this.message2 = message2;
		this.message3 = message3;
	}

	@Override
	public void initGui() {
		this.buttonList.clear();
		this.buttonList.add(
			new GuiButton(0, this.width / 2 - 100, this.height / 4 + 120 + 12, "Close")
			);
	}

	@Override
	public void drawScreen(int par1, int par2, float par3) {
		this.drawDefaultBackground();
		this.drawCenteredString(
				this.fontRendererObj,
				message1,
				this.width / 2, this.height / 2 - 40,
				11184810);
		this.drawCenteredString(
				this.fontRendererObj,
				message2,
				this.width / 2, this.height / 2 - 20,
				11184810);
		this.drawCenteredString(
				this.fontRendererObj,
				message3,
				this.width / 2, this.height / 2 - 0,
				11184810);

		super.drawScreen(par1, par2, par3);
	}

	@Override
	protected void actionPerformed(GuiButton par1GuiButton) {
		if (par1GuiButton.id == 0) {
			System.exit(0);
		}
	}
}
