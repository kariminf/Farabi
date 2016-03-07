/* FARIS : Factual Arrangement and Representation of Ideas in Sentences
 * FAris : Farabi & Aristotle
 * Faris : A knight (in Arabic)
 * --------------------------------------------------------------------
 * Copyright (C) 2015 Abdelkrime Aries (kariminfo0@gmail.com)
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 *  
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


package kariminf.faris.ston;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import kariminf.faris.knowledge.Mind;
import kariminf.faris.knowledge.Mind.Truth;
import kariminf.faris.linguistic.Adjective;
import kariminf.faris.linguistic.Verb;
import kariminf.faris.linguistic.Verb.Aspect;
import kariminf.faris.linguistic.Verb.Tense;
import kariminf.faris.philosophical.Action;
import kariminf.faris.philosophical.Quality;
import kariminf.faris.philosophical.Substance;
import kariminf.sentrep.ston.Parser;


/**
 * 
 * @author Abdelkrime Aries (kariminfo0@gmail.com)
 *         <br>
 *         Copyright (c) 2015 Abdelkrime Aries
 *         <br><br>
 *         Licensed under the Apache License, Version 2.0 (the "License");
 *         you may not use this file except in compliance with the License.
 *         You may obtain a copy of the License at
 *         <br><br>
 *         http://www.apache.org/licenses/LICENSE-2.0
 *         <br><br>
 *         Unless required by applicable law or agreed to in writing, software
 *         distributed under the License is distributed on an "AS IS" BASIS,
 *         WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *         See the License for the specific language governing permissions and
 *         limitations under the License.
 */
public class FarisParse extends Parser {

	private HashSet<Substance> substances;
	private HashSet<Action> actions;
	private HashMap<String, Mind> minds;
	
	private Substance currentPlayer;
	//private String currentPlayerID;
	private HashMap<String, Substance> _players = new HashMap<String, Substance>();
	
	private Action currentAction;
	//private String currentPlayerID;
	private HashMap<String, Action> _actions = new HashMap<String, Action>();
	
	private Set<Substance> conjunctions = new HashSet<Substance>();
	private boolean subject = true;
	
	public FarisParse(HashSet<Substance> substances, HashSet<Action> actions, HashMap<String, Mind> minds){
		this.substances = substances;
		this.actions = actions;
		this.minds = minds;
	}

	@Override
	protected void addAction(String id, int synSet) {
		Verb verb = new Verb(synSet);
		currentAction = Action.getNew(verb);
		_actions.put(id, currentAction);
		
	}

	@Override
	protected void addVerbSpecif(String tense, String modality,
			boolean progressive, boolean negated) {
		Verb verb = currentAction.getVerb();
		verb.setTense(Tense.valueOf(tense));
		//verb.setAspect(Aspect.valueOf(aspect));
	}

	@Override
	protected void endAction() {
	}

	@Override
	protected void actionFail() {
	}

	@Override
	protected void addRole(String id, int synSet) {
		currentPlayer = new Substance(synSet);
		_players.put(id, currentPlayer);
		/*
		if (! players.containsKey(id)){
			players.put(id, currentPlayer);
		}*/
	}

	@Override
	protected void addAdjective(int synSet, Set<Integer> advSynSets) {
		Adjective adj = new Adjective(synSet);
		Quality quality = new Quality(adj);
		quality.setAdverbsInt(advSynSets);
		currentPlayer.addQuality(quality);
		
	}

	@Override
	protected void adjectiveFail() {
	}

	@Override
	protected void roleFail() {
		
	}

	@Override
	protected void parseSuccess() {
		
		for(Substance sub : _players.values()){
			substances.add(sub);
		}
		
		for(Action action: _actions.values()){
			actions.add(action);
			Mind defaultMind = minds.get("Default");
			defaultMind.addAction(Truth.FACT, action);
		}
		
		
	}

	@Override
	protected void endActions() {
		// TODO Auto-generated method stub
		
	}


	@Override
	protected void addConjunctions(Set<String> IDs) {
		for (String roleID: IDs)
		if (_players.containsKey(roleID)){
			Substance role = _players.get(roleID);
			conjunctions.add(role);
		}
	}


	@Override
	protected void addSubjects() {
		conjunctions = new HashSet<Substance>();
		
	}

	@Override
	protected void endSubjects() {
		currentAction.addConjunctSubjects(conjunctions);
		
	}

	@Override
	protected void addActionAdverb(int advSynSet, Set<Integer> advSynSets) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void adverbFail() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void addObjects() {
		conjunctions = new HashSet<Substance>();
		
	}

	@Override
	protected void endObjects() {
		currentAction.addConjunctObjects(conjunctions);
		
	}

	@Override
	protected void addRoleSpecif(String name, String def, String quantity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void parseFail() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void relativeFail() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void addRelative(String type) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void addComparison(String type, Set<Integer> adjSynSets) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void beginSentence(String type) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void endSentence() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void beginActions(boolean mainClause) {
		// TODO Auto-generated method stub
		
	}

}