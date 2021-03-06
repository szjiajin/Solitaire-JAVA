package Solitaire;

public class TableauDraggedState implements CardDraggedState {
	protected final static CardDraggedState INSTANCE = new TableauDraggedState();

	public void droppedCardToTableau(SolitaireGame game, int col) {
		int currentLast = game.numberOfCardsInColumn(col);
		Card firstCard = game.draggedCards.get(0);
		if (game.tableau[col].isEmpty()){ //empty column
			if (firstCard.getCardNum()==13){
				successfullyAddToColumn(game,col);
			}else{
				game.moveBackDragged();
			}
		}else{
			Card lastCard = game.tableau[col].getCardAtIndex(currentLast-1);
			//different colour and number decrease by 1
			if ((firstCard.getCardNum()==lastCard.getCardNum()-1)&&(firstCard.isRed()!=lastCard.isRed())){
				successfullyAddToColumn(game,col);
			}else{
				game.moveBackDragged();
			}
		}
	}
	
	private void successfullyAddToColumn(SolitaireGame game, int col){
		while (!game.draggedCards.isEmpty()){
			game.tableau[col].appendCard(game.draggedCards.remove(0));
		}
		game.tableau[game.draggedColumn].deleteCardAtIndex(game.draggedRow);
		game.draggedCards.clear();
	}
	
	private void successfullyAddToFoundation(SolitaireGame game, int col){
		while (!game.draggedCards.isEmpty()){
			game.foundationPile[col].appendCard(game.draggedCards.remove(0));
		}
		game.tableau[game.draggedColumn].deleteCardAtIndex(game.draggedRow);
		game.draggedCards.clear();
	}
	public void droppedCardToFoundation(SolitaireGame game, int col) {
		if (game.draggedCards.size()==1){
			Card card = game.draggedCards.get(0);
			int currentLast = game.foundationPile[col].size();
			//empty foundation and Ace
			if (currentLast==0&&card.getCardNum()==1){
				successfullyAddToFoundation(game,col);
				//non-empty and satisfy requirement
			}else if (currentLast>0&&card.getCardNum()==(game.foundationPile[col].getCardAtIndex(currentLast-1).getCardNum()+1)&&card.getSuit()==game.foundationPile[col].getCardAtIndex(currentLast-1).getSuit()){
					successfullyAddToFoundation(game,col);
			}else{
				game.moveBackDragged();
			}
		}else{
			game.moveBackDragged();
		}
	}

}
