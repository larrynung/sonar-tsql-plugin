package org.sonar.plugins.tsql.rules.definitions;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import org.sonar.api.server.rule.RulesDefinition;
import org.sonar.api.server.rule.RulesDefinitionXmlLoader;
import org.sonar.plugins.tsql.languages.TSQLLanguage;

public final class TsqlMsRulesDefinition implements RulesDefinition {

	public static final String KEY = "mssql";
	
	protected static final String NAME = "Microsoft T-SQL";

	protected String rulesDefinitionFilePath() {
		return "/rules/vssql-rules.xml";
	}

	private void defineRulesForLanguage(final Context context, final String repositoryKey, final String repositoryName,
			String languageKey) {
		final NewRepository repository = context.createRepository(repositoryKey, languageKey).setName(repositoryName);

		final InputStream rulesXml = this.getClass().getResourceAsStream(rulesDefinitionFilePath());
		if (rulesXml != null) {
			final RulesDefinitionXmlLoader rulesLoader = new RulesDefinitionXmlLoader();
			rulesLoader.load(repository, rulesXml, StandardCharsets.UTF_8.name());
		}
		
		repository.done();
	}

	@Override
	public void define(final Context context) {
		String repositoryKey = TsqlMsRulesDefinition.getRepositoryKeyForLanguage(TSQLLanguage.KEY);
		String repositoryName = TsqlMsRulesDefinition.getRepositoryNameForLanguage(TSQLLanguage.KEY);
		defineRulesForLanguage(context, repositoryKey, repositoryName, TSQLLanguage.KEY);
	}

	public static String getRepositoryKeyForLanguage(final String languageKey) {
		return languageKey.toLowerCase() + "-" + KEY;
	}

	public static String getRepositoryNameForLanguage(final String languageKey) {
		return languageKey.toUpperCase() + " " + NAME;
	}

}
