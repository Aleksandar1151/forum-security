import { ForumComment } from "./comment";

export interface Topic {
  id?: number;
  title: string;
  description: string;
  comments?: ForumComment[];
}
