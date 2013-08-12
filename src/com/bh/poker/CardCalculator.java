package com.bh.poker;

public class CardCalculator {
	public static enum HandType {
		HighCard(1, "High Card"), OnePair(2, "One Pair"), TwoPair(3, "Two Pair"), ThreeOfAKind(4, "Three of a Kind"), Straight(5, "Straight"), Flush(6, "Flush"), FullHouse(7, "Full House"), FourOfAKind(8, "Four of a Kind"), StraightFlush(9, "Straight Flush!"), RoyalFlush(10, "Royal Flush!!!");
		
		private int val;
		private String rep;
		HandType(int v, String r) {
			val = v;
			rep = r;
		}
		
		public int getVal() {
			return val;
		}
		
		public String toString() {
			return rep;
		}
		
		public String getRep() {
			return rep;
		}
	}
	
	private static HandType set(HandType t, HandType a) {
		if(t.getVal() > a.getVal()) {
			return t;
		} else {
			return a;
		}
	}
	
	public static Object[] getVal(Card... cards) {
		
		if(cards.length == 0) return null;
		
		
		int card = 0;
		HandType type = HandType.HighCard;
		
		//One / Two Pair
		int pairs = 0;
		int highCard = 0;
		for(Card c1 : cards) {
			for(Card c2 : cards) {
				if(c1.equals(c2)) continue;
				if(c1.getVal() == c2.getVal()) {
					pairs += 1;
					highCard = Math.max(highCard, c1.getVal());
				}
			}
		}
		pairs /= 2;
		if(pairs == 1) {
			type = set(type, HandType.OnePair);
			card = highCard;
		}
		if(pairs > 1) {
			type = set(type, HandType.TwoPair);
			card = highCard;
		}
		
		//Three of a kind
		int triples = 0;
		int trival = 0;
		for(Card c1 : cards) {
			c2 : for(Card c2 : cards) {
				for(Card c3 : cards) {
					if(c1.equals(c2)) continue c2;
					if(c1.equals(c3)) continue;
					if(c3.equals(c2)) continue;
					
					if(c1.getVal() == c2.getVal() && c2.getVal() == c3.getVal()) {
						triples += 1;
						highCard = c1.getVal();
						trival = highCard;
					}
				}
			}
		}
		
		triples /= 6;
		if(triples > 0) {
			type = set(type, HandType.ThreeOfAKind);
			card = highCard;
			pairs -= 3;
		}
		

		//Straight
		int workCount = 0;
		boolean works;
		for(int i = 2; i <= 14; i++) {
			works = false;
			for(Card c : cards) {
				if(c.getVals().contains(i)) {
					works = true;
				}
			}
			if(works) {
				workCount++;
			} else {
				workCount = 0;
			}
			if(workCount == 5) {
				type = set(type, HandType.Straight);
				card = i;
				break;
			}
		}
		
		//Royal / Straight Flush
		works = false;
		int start = 0;
		for(int j = 0; j < 4; j++) {
			for(int i = 2; i <= 14; i++) {
				if(works == false) {
					start = i;
				}
				works = false;
				for(Card c : cards) {
					if(c.getVals().contains(i) && c.getSuit() == j) {
						works = true;
					}
				}
				if(works) {
					workCount++;
				} else {
					workCount = 0;
				}
				if(workCount == 5) {
					type = set(type, HandType.StraightFlush);
					card = i;
					if(start == 10) {
						type = set(type, HandType.RoyalFlush);
						card = i;
					}
					break;
				}
			}
		}
		
		
		//flush
		
		int[] suitCount = new int[] {0, 0, 0, 0};
		
		for(int i = 0; i < suitCount.length; i++) {
			for(Card c : cards) {
				if(c.getSuit() == i) {
					suitCount[i] += 1;
				}
			}
		}
		
		for(int i = 0; i < suitCount.length; i++) {
			if(suitCount[i] >= 5) {
				type = set(type, HandType.Flush);
				card = 0;
			}
		}
		
		if(pairs >= 1 && triples >= 1) {
			type = set(type, HandType.FullHouse);
			card = trival;
		}
		
		//Four of a kind
		
		int fours = 0;
		int[] suitsFound;
		for(int i = 2; i <= 14; i++) {
			suitsFound = new int[] {0, 0, 0, 0};
			for(Card c : cards) {
				if(!c.getVals().contains(i)) continue;
				if(suitsFound[c.getSuit()] == 1) continue;
				suitsFound[c.getSuit()] = 1;
			}
			works = true;
			for(int j = 0; j < 4; j++) {
				if(suitsFound[j] == 0) works = false;
			}
			if(works) {
				fours += 1;
				highCard = i;
				break;
			}
		}
		if(fours > 0) {
			type = set(type, HandType.FourOfAKind);
			card = highCard;
		}
		
		return new Object[] {type, card};
	}
}
