package com.topicus.CFPApplication.domain;

import java.sql.Blob;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.topicus.CFPApplication.domain.PresentationDraft.Label;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@Entity
@ApiModel(value = "Conference", description = "Holds all value for the conference")
public class Conference {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@ApiModelProperty(position = 1, required = true, value = "The unique identifier for the conference", hidden = true)
	private long id;

	@ApiModelProperty(position = 2, required = true)
	private String name;

	@ApiModelProperty(position = 4, required = true, example = "2018-05-23T01:20:30")
	private LocalDateTime startDate;
	@ApiModelProperty(position = 5, required = true, example = "2018-05-23T01:20:30")
	private LocalDateTime endDate;
	@ApiModelProperty(position = 6, required = true, value = "After this day the organizor can make a definitive selection of all presentations, "
			+ "and presentations can no longer be submitted", example = "2018-05-23T01:20:30")
	private LocalDateTime deadlinePresentationDraft;

	@Autowired
	@Column(name = "category")
	@ElementCollection(fetch = FetchType.EAGER)
	@JoinColumn(name = "conference_id")
	@ApiModelProperty(position = 7, value = "Categories that can be assigned to presentations will be added here")
	private Set<String> categories;

	@Autowired
	@Column(name = "stage")
	@OneToMany(fetch = FetchType.EAGER)
	@JoinColumn(name = "conference_id")
	@ApiModelProperty(position = 8, value = "Available stages for the presentations will be added here", hidden = true)
	private Set<Stage> stages;

	@Autowired
	@Column(name = "extra")
	@OneToMany(fetch = FetchType.EAGER)
	@JoinColumn(name = "conference_id")
	@ApiModelProperty(position = 9, value = "Extra facilities for the conference will be added here", hidden = true)
	private Set<Extra> extras;

	@Column(name = "day")
	@OneToMany(fetch = FetchType.EAGER)
	@JoinColumn(name = "conference_id")
	@ApiModelProperty(position = 10, value = "Duration in days", hidden = true)
	private Set<Day> days = new HashSet<Day>();

	@Column(name = "mail_template")
	@OneToMany(fetch = FetchType.EAGER)
	@JoinColumn(name = "conference_id")
	@ApiModelProperty(position = 11, value = "Available e-mail templates", hidden = true)
	private Set<MailTemplate> mailTemplates = new HashSet<MailTemplate>();

	@Column(name = "presentationdraft")
	@OneToMany(fetch = FetchType.EAGER)
	@JoinColumn(name = "conference_id")
	@ApiModelProperty(position = 12, value = "All presentations from this conference will be added to this list", hidden = true)
	private Set<PresentationDraft> presentationDrafts = new HashSet<PresentationDraft>();

	@OneToOne
	@ApiModelProperty(position = 13, value = "Form to be used for this conference", hidden = true)
	private PresentationDraftForm presentationDraftForm;

	public void addCategorie(String categorie) {
		this.categories.add(categorie);
	}
	
	public void addStage(Stage stage) {
		this.stages.add(stage);
	}

	public void addExtra(Extra extra) {
		this.extras.add(extra);
	}

	public void addDay(Day day) {
		this.days.add(day);
	}

	public void addMailTemplate(MailTemplate mailTemplate) {
		this.mailTemplates.add(mailTemplate);
	}

	public void addPresentationDraft(PresentationDraft presentationDraft) {
		this.presentationDrafts.add(presentationDraft);
	}

	// Getters en Setters:
	public Set<Stage> getStages() {
		return stages;
	}

	public LocalDateTime getDeadlinePresentationDraft() {
		return deadlinePresentationDraft;
	}

	public void setDeadlinePresentationDraft(LocalDateTime deadlinePresentationDraft) {
		this.deadlinePresentationDraft = deadlinePresentationDraft;
	}

	public Set<Extra> getExtras() {
		return extras;
	}

	public void setExtras(Set<Extra> extras) {
		this.extras = extras;
	}

	public Set<Day> getDays() {
		return days;
	}

	public void setDays(Set<Day> days) {
		this.days = days;
	}

	public void setStages(Set<Stage> stages) {
		this.stages = stages;
	}

	public Set<String> getCategories() {
		return categories;
	}

	public void setCategories(Set<String> categories) {
		this.categories = categories;
	}

	public LocalDateTime getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDateTime startDate) {
		this.startDate = startDate;
	}

	public LocalDateTime getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDateTime endDate) {
		this.endDate = endDate;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Set<PresentationDraft> getPresentationDrafts() {
		return presentationDrafts;
	}

	public void setPresentationDrafts(Set<PresentationDraft> presentationDrafts) {
		this.presentationDrafts = presentationDrafts;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public PresentationDraftForm getPresentationDraftForm() {
		return presentationDraftForm;
	}

	public void setPresentationDraftForm(PresentationDraftForm presentationDraftForm) {
		this.presentationDraftForm = presentationDraftForm;
	}

	@Entity
	@ApiModel(value = "Extra", description = "Any extra facilities that will be available for the conference")
	public static class Extra {

		@Id
		@ApiModelProperty(position = 1, value = "Unique identifier for the extra facility")
		private String name;

		@ApiModelProperty(position = 2, value = "The description of the extra facility")
		private String description;

		@ApiModelProperty(position = 3, value = "How long the extra facility will by available for the duration of the conference", hidden = true)
		private Period duration;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}

		public Period getDuration() {
			return duration;
		}

		public void setDuration(Period duration) {
			this.duration = duration;
		}
	}

	@Entity
	@ApiModel(value = "Stage", description = "Holds name and attributes for a stage")
	public static class Stage {

		@Id
		@ApiModelProperty(position = 1, value = "Unique identifier for the stage")
		private String name;

		@Autowired
		@Column(name = "attribute")
		@OneToMany(fetch = FetchType.EAGER)
		@JoinColumn(name = "stage_name")
		@ApiModelProperty(position = 2, value = "Attributes of the stage will be added here")
		private Set<Attribute> attributes;

		@ManyToOne
		@ApiModelProperty(position = 3, value = "Holds the conference to which this stage belongs", hidden = true)
		private Conference conference;

		public Set<Attribute> getAttributes() {
			return attributes;
		}

		public void setAttributes(Set<Attribute> attributes) {
			this.attributes = attributes;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		@Entity
		@ApiModel(value = "Attribute", description = "Holds attribute values for the stage")
		public static class Attribute {

			@Id
			@ApiModelProperty(position = 1, value = "Unique identifier for the attribute")
			private String name;

			@ApiModelProperty(position = 2, value = "Amount of the item needed")
			private int amount;

			// onderzoek hoe dit werkt
			@ApiModelProperty(position = 3, value = "The image for this attribute", hidden = true)
			private Blob icon;

			@ManyToOne
			@ApiModelProperty(position = 4, value = "The stage to which this attribute belongs", hidden = true)
			private Stage stage;

			public Blob getIcon() {
				return icon;
			}

			public Stage getStage() {
				return stage;
			}

			public void setStage(Stage stage) {
				this.stage = stage;
			}

			public void setIcon(Blob icon) {
				this.icon = icon;
			}

			public int getAmount() {
				return amount;
			}

			public void setAmount(int amount) {
				this.amount = amount;
			}

			public String getName() {
				return name;
			}

			public void setName(String name) {
				this.name = name;
			}

		}

	}

	@Entity
	@ApiModel(value = "MailTemplate", description = "Holds the value to fill in an e-mail template")
	public static class MailTemplate {

		@Id
		@ApiModelProperty(position = 1, value = "Unique identifier for the template and type of the template")
		private Label label;

		@ApiModelProperty(position = 3, value = "Content of the mail")
		private String content;

		public String getContent() {
			return content;
		}

		public void setContent(String content) {
			this.content = content;
		}

		public Label getLabel() {
			return this.label;
		}

		public void setLabel(Label label) {
			this.label = label;
		}

	}

}
