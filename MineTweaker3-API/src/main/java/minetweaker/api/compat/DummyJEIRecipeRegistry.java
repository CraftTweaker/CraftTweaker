package minetweaker.api.compat;

public class DummyJEIRecipeRegistry implements IJEIRecipeRegistry {
	
	@Override
	public void addRecipe(Object object) {
		System.out.println("I am a dummy and cannot add recipes");
	}
	
	@Override
	public void removeRecipe(Object object) {
		System.out.println("I am a dummy and cannot remove recipes");
	}
}
