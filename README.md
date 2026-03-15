# LesVeilleurs Discord Bot

Discord bot développé pour la communauté **Les Veilleurs de Monde (LVDM)**.

Ce bot fournit des fonctionnalités adaptées aux besoins de la communauté.  
La première fonctionnalité implémentée permet la **création automatique de salons vocaux temporaires**.

---

# ✨ Fonctionnalités

## Salons vocaux temporaires

Le bot permet aux utilisateurs de créer automatiquement leur propre salon vocal temporaire.

### Fonctionnement

1. Un salon vocal spécial est créé :  
   `🍻 Create discussion`

2. Une catégorie dédiée est créée :  
   `== Temporary Discussions ==`

3. Lorsqu'un utilisateur rejoint le salon `🍻 Create discussion` :
    - un **salon vocal temporaire** est automatiquement créé
    - l'utilisateur est déplacé dans ce salon
    - il obtient les permissions pour le modifier

4. Lorsque le salon devient vide :
    - il est **automatiquement supprimé**

Cela permet aux membres de créer facilement des discussions privées sans encombrer le serveur avec des salons permanents.

---

# ⚙️ Commandes

Toutes les commandes nécessitent la permission **Administrator**.

| Commande | Description |
|--------|--------|
| `/lvdm setup` | Configure les salons nécessaires au fonctionnement du bot |
| `/lvdm clear` | Supprime les salons temporaires créés par le bot |
| `/lvdm ping` | Vérifie si le bot est en ligne |

---

# 🛠 Technologies utilisées

- **Java 24**
- **JDA (Java Discord API)**
- **Google Cloud Run**
- **GitHub**
- **docker**

---

# 🚀 Déploiement

Le bot est conçu pour être déployé sur **Google Cloud Run**.

Variables nécessaires :

DISCORD_TOKEN=your_bot_token

---

# 🤝 Contribution

Les contributions sont les bienvenues.

1. Fork le repository
2. Crée une branche
3. Propose une Pull Request

---

# 📜 Licence

Projet développé pour la communauté **Les Veilleurs de Monde**.