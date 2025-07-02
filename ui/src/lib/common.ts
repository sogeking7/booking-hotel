import {UserRole} from './booking-hotel-api';

export function getRoleTagColor(role: UserRole): string {
  switch (role) {
    case UserRole.admin:
      return 'purple';
    case UserRole.user:
      return 'blue';
    default:
      return 'default';
  }
}
